$(function () {
    let productId = getQueryString('productId');
    let shopId = getQueryString('shopId');
    let infoUrl = '/o2o/shop/getproductbyid?productId=' + productId;
    let categoryUrl = '/o2o/shop/getproductcategorylistbyshopId?shopId=' + shopId;
    let productPostUrl = '/o2o/shop/modifyproduct';
    let isEdit = false;
    if (productId) { //为修改页面
        getInfo(productId);
        isEdit = true;
    } else { //为新增页面
        getCategory(shopId);
        productPostUrl = '/o2o/shop/addproduct';
    }

    function getInfo(id) {
        $.getJSON(infoUrl, function (data) {
            if (data.success) {
                let product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(
                    product.promotionPrice);

                let optionHtml = '';
                let optionArr = data.productCategoryList;
                let optionSelected = product.productCategory.productCategoryId;
                optionArr
                    .map(function (item, index) {
                        let isSelect = optionSelected === item.productCategoryId ? 'selected'
                            : '';
                        optionHtml += '<option data-value="'
                            + item.productCategoryId
                            + '"'
                            + isSelect
                            + '>'
                            + item.productCategoryName
                            + '</option>';
                    });
                $('#category').html(optionHtml);
            }
        });
    }

    function getCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.success) {
                let productCategoryList = data.productCategoryList;
                let optionHtml = '';
                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(
        function () {
            let product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#priority').val();
            product.normalPrice = $('#normal-price').val();
            product.promotionPrice = $('#promotion-price').val();
            product.productCategory = {
                productCategoryId: $('#category').find('option').not(
                    function () {
                        return !this.selected;
                    }).data('value')
            };
            product.productId = productId;

            let thumbnail = $('#small-img')[0].files[0];
            console.log(thumbnail);
            let formData = new FormData();
            formData.append('thumbnail', thumbnail);
            $('.detail-img').map(
                function (index, item) {
                    if ($('.detail-img')[index].files.length > 0) {
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            formData.append('productStr', JSON.stringify(product));
            let verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            $.ajax({
                url: productPostUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('提交失败！');
                        console.log(JSON.stringify(data))
                        $('#captcha_img').click();
                    }
                }
            });
        });

});
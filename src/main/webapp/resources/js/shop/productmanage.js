$(function () {
    let shopId = getQueryString('shopId');
    let getproductlistUrl = '/o2o/shop/getproductlist?pageIndex=1&pageSize=9999&shopId=' + shopId;
    let deleteUrl = '/o2o/shop/modifyproduct';

    getList();

    function getList() {
        $.getJSON(getproductlistUrl, function (data) {
            if (data.success) {
                let productList = data.productList;
                let tempHtml = '';
                productList.map(function (item, index) {
                    let textOp = "下架";
                    let contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml +=
                        `<div class="row row-product">
                            <div class="col-33">
                                ${item.productName}
                            </div>
                            <div class="col-33">
                                ${item.priority}
                            </div>
                            <div class="col-33">
                                <a href="#" class="edit" data-id="${item.productId}" data-status="${item.enableStatus}">编辑</a>
                                <a href="#" class="delete" data-id="${item.productId}" data-status="${contraryStatus}">${textOp}</a>
                                <a href="#" class="preview" data-id="${item.productId}" data-status="${item.enableStatus}">预览</a>
                            </div>
                        </div>`;
                });
                $('.product-wrap').html(tempHtml);
            }
        });
    }


    function deleteItem(id, enableStatus) {
        let product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定下架该商品?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }


    $('.product-wrap').on('click', 'a', function (e) {
        let target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/o2o/shopadmin/productedit?productId=' + e.currentTarget.dataset.id;
        } else if (target.hasClass('delete')) {
            deleteItem(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
        } else if (target.hasClass('preview')) {
            window.location.href = '/o2o/frontend/productdetail?productId=' + e.currentTarget.dataset.id;
        }
    });

    $('#new').click(function () {
        window.location.href = '/o2o/shopadmin/productedit?shopId=' + shopId;
    });
});
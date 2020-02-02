/*
*
* */
$(function () {
    let shopId = getQueryString('shopId');
    let isEdit = shopId ? true : false;

    const initUrl = '/o2o/shop/getshopinitinfo';
    const registerShopUrl = '/o2o/shop/registershop';
    const shopInfoUrl = '/o2o/shop/getshopbyid?shopId=' + shopId;
    const modifyShopUrl = '/o2o/shop/modifyshop';
    if (isEdit) {
        getShopInfo(shopId);
    } else {
        getShopInitInfo();
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                let shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                let shopCategory = `<option data-id="${shop.shopCategory.shopCategoryId}" selected>${shop.shopCategory.shopCategoryName}</option>`;
                let tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += `<option data-id="${item.areaId}">${item.areaName}</option>`;
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                $(`#area option[data-id="${shop.area.areaId}"]`).attr('selected', 'selected');
            }
        });
    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                let tempHtml = '';
                let tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += `<option data-id="${item.shopCategoryId}">${item.shopCategoryName}</option>`;
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += `<option data-id="${item.areaId}">${item.areaName}</option>`;
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        })
    }

    $('#submit').click(function () {
        let shop = {};
        if(isEdit){
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();

        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        let shopImg = $("#shop-img")[0].files[0];
        let formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        let verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url: isEdit ? modifyShopUrl : registerShopUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                } else {
                    $.toast('提交失败！' + data.errMsg);
                }

                $('#captcha_img').click();

            }
        })

    })

})
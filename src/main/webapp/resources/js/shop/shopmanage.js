

$(function () {
    let shopId = getQueryString('shopId');
    let shopInfoUrl = '/o2o/shop/getshopmanagementinfo?shopId='+shopId;
    $.getJSON(shopInfoUrl,function (data) {
        if(data.redirect){//非法跳转，返回列表
            window.location.href = data.url;
        }else{
            if(data.shopId != null && data.shopId != undefined){
                shopId = data.shopId;
            }
            $('#shopInfo').attr('href','/o2o/shopadmin/shopedit?shopId='+shopId);
            $('#categoryManage').attr('href','/o2o/shopadmin/productcategorymanage?shopId='+shopId);
            $('#productManage').attr('href','/o2o/shopadmin/productmanage?shopId='+shopId);

        }
    })
})
$(function () {
    let productId = getQueryString('productId');
    let productUrl = '/o2o/frontend/getproductdetailpageinfo?productId='+ productId;
    
    $.getJSON(
        productUrl,
        function (data) {
            if (data.success) {
                let product = data.product;
                $('#product-img').attr('src', product.imgAddr);
                $('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
                $('#product-name').text(product.productName);
                $('#product-desc').text(product.productDesc);
                let imgListHtml = '';
                product.productImgList.map(function (item, index) {
                    imgListHtml += '<div> <img src="'
                        + item.imgAddr + '"/></div>';
                });
                // 生成购买商品的二维码供商家扫描
                imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
                    + product.productId + '"/></div>';
                $('#imgList').html(imgListHtml);
            }
        });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});

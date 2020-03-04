$(function () {
    /*
    * 配置tomcat以此访问本地图片
    * <Context docBase="/Users/wangyihang/Pictures/com.bushengxin.o2o/images/upload" path="/upload" reloadable="true"/>
    * */
    let url = '/o2o/frontend/getmainpageinfo';

    $.getJSON(url, function (data) {
        if (data.success) {
            let headLineList = data.headLineList;
            let swiperHtml = '';
            headLineList.map(function (item, index) {
                swiperHtml +=
                    `<div class="swiper-slide img-wrap">
                         <img class="banner-img" src="${item.lineImg}" alt="${item.lineName}">
                     </div>`;
            });
            $('.swiper-wrapper').html(swiperHtml);
            $(".swiper-container").swiper({
                autoplay: 5000,
                autoplayDisableOnInteraction: false
            });
            let shopCategoryList = data.shopCategoryList;
            let categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                categoryHtml +=
                    `<div class="col-50 shop-classify" data-category='${item.shopCategoryId}'>
                         <div class="word">
                             <p class="shop-title">${item.shopCategoryName}</p>
                             <p class="shop-desc">${item.shopCategoryDesc}</p>
                         </div>
                         <div class="shop-classify-img-warp">
                            <img class="shop-img" src="${item.shopCategoryImg}">
                         </div>
                    </div>`;
            });
            $('.row').html(categoryHtml);
        }
    });

    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    $('.row').on('click', '.shop-classify', function (e) {
        let shopCategoryId = e.currentTarget.dataset.category;
        let newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });

});

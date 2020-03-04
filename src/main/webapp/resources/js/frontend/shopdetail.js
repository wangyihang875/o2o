$(function () {
    let loading = false;
    let maxItems = 999;
    let pageSize = 5;
    let getProductlistUrl = '/o2o/frontend/getproductlist';
    let pageNum = 1;
    let shopId = getQueryString('shopId');
    let productCategoryId = '';
    let productName = '';
    let getshopdetailpageinfo = '/o2o/frontend/getshopdetailpageinfo?shopId=' + shopId;

    function getSearchDivData() {
        $.getJSON(getshopdetailpageinfo, function (data) {
            if (data.success) {
                let shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.shopImg);
                $('#shop-update-time').html(
                    new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                $('#shop-name').html(shop.shopName);
                $('#shop-desc').html(shop.shopDesc);
                $('#shop-addr').html(shop.shopAddr);
                $('#shop-phone').html(shop.phone);

                let productCategoryList = data.productCategoryList;
                let html = '';
                productCategoryList.map(function (item, index) {
                    html += '<a href="#" class="button" data-product-search-id='
                        + item.productCategoryId
                        + '>'
                        + item.productCategoryName
                        + '</a>';
                });
                $('#shopdetail-button-div').html(html);
            }
        });
    }

    getSearchDivData();

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        let url = getProductlistUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                let html = '';
                data.productList.map(function (item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.imgAddr + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                let total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }

    addItems(pageSize, pageNum);

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    $('#shopdetail-button-div').on('click', '.button',
        function (e) {
            //添加无限滚动事件监听器
            $.attachInfiniteScroll($('.infinite-scroll'));
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });

    $('.list-div').on('click', '.card',
            function (e) {
                let productId = e.currentTarget.dataset.productId;
                window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
            });

    $('#search').on('change', function (e) {
        //添加无限滚动事件监听器
        $.attachInfiniteScroll($('.infinite-scroll'));
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});

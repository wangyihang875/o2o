

$(function () {
    const getShopListUrl = '/o2o/shop/getshoplist';

    getShopList();

    function getShopList() {
        $.ajax({
            url : getShopListUrl,
            type : "get",
            dataType : "json",
            success : function(data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);
                }else{
                    $.toast(data.errMsg);
                }
            },
            error:function (e) {
                console.log(JSON.stringify(e))
            }
        });
    }

    function handleList(data) {
        let html ='';
        data.map(function (item,index) {
            html += `<div class="row row-shop">
                        <div class="col-40">${item.shopName}</div>
                        <div class="col-40">${shopStatus(item.enableStatus)}</div>
                        <div class="col-20">${goShop(item.enableStatus,item.shopId)}</div>
                    </div>`;
        })
        $('.shop-wrap').html(html);
    }

    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function shopStatus(status) {
        if (status == 0) {
            return '审核中';
        } else if (status == -1) {
            return '店铺非法';
        } else if(status == 1){
            return '审核通过';
        }
    }

    function goShop(status, id) {
        if (status != 0 && status != -1) {
            return '<a href="/o2o/shopadmin/shopmanage?shopId='+ id +'">进入</a>';
        } else {
            return '';
        }
    }
})
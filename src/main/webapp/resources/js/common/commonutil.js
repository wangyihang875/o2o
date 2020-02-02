
/*
* 验证码验证
* */
function changeVerifyCode(img) {
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

/*
*获取当前url上指定参数的值
* */
function getQueryString(name) {
	let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	let r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}
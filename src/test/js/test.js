let tnbOptions=[
    {name:'无症状',selected:false},
    {name:'多饮',selected:false},
    {name:'多食',selected:false},
    {name:'多屎',selected:false},
    {name:'视力模糊',selected:false},
    {name:'感染',selected:false},
    {name:'手脚麻木',selected:false},
    {name:'下肢浮肿',selected:false},
    {name:'小便泡沫异味',selected:false},
    {name:'体重下降明显',selected:false},
    {name:'其他',selected:false},
]

function getSelectedOptions(options){
    let temp = []
    // temp = options.filter((item)=>{
    //     return item.selected
    // })
    options.map((item,index)=>{
        if(item.selected){
            temp.push(item.name)
        }
    })
    return temp.join(',')
}

let t = ''
t.split(',')

// console.log('='+getSelectedOptions(tnbOptions))
//数据转化
function formatNumber(n) {
    n = n.toString()
    return n[1] ? n : '0' + n
}

function formatTime(number, format) {

    var formateArr = ['Y', 'M', 'D', 'h', 'm', 's'];
    var returnArr = [];

    var date = new Date(number * 1000);
    returnArr.push(date.getFullYear());
    returnArr.push(formatNumber(date.getMonth() + 1));
    returnArr.push(formatNumber(date.getDate()));

    returnArr.push(formatNumber(date.getHours()));
    returnArr.push(formatNumber(date.getMinutes()));
    returnArr.push(formatNumber(date.getSeconds()));

    for (var i in returnArr) {
        format = format.replace(formateArr[i], returnArr[i]);
    }
    return format;
}

console.log('#='+formatTime(new Date().getTime()),'YYYY-MM-DD')

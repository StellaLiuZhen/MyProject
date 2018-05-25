var allPrice = 0.0;         //保存总价格
window.onload = function () {     //在页面加载完毕之后进行统计计算
    document.getElementById("result").innerHTML =  "<font color = 'red'>" + allPrice + "</font>" ;
}
function calGoods(gid) {
    var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
    var count = parseInt(document.getElementById(gid).value) ;
    var num = price * count ;
    num = Math.round(num*100)/100;
    allPrice += num ;
    document.getElementById("cal-" + gid).innerHTML = "<font color = 'red'>" + num + "</font>";
    if(document.getElementById("result") != undefined){
        document.getElementById("result").innerHTML =  "<font color = 'red'>" + allPrice + "</font>" ;
    }
}

function addBut(gid) {
    var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
    var count = parseInt(document.getElementById(gid).value) ;
    allPrice -= (price * count) ;
    count ++ ;
    document.getElementById(gid).value = count ;
    calGoods(gid) ;
}
function subBut(gid) {
    var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
    var count = parseInt(document.getElementById(gid).value) ;
    allPrice -= (price * count) ;
    if(count != 0){
        count -- ;
    }
    document.getElementById(gid).value = count ;
    calGoods(gid);
}
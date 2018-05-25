var allPrice = 0.0;         //保存总价格
window.onload = function () {     //在页面加载完毕之后进行统计计算
    document.getElementById("result").innerHTML =  "<font color = 'red'>" + allPrice + "</font>" ;
}
function calGoods(gid) {
    var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
    var count = parseInt(document.getElementById(gid).value) ;
    var num = accMul(price,count);
    num.toFixed(2);
    allPrice = accAdd(allPrice,num);
    allPrice.toFixed(2);
    document.getElementById("cal-" + gid).innerHTML = "<font color = 'red'>" + num + "</font>";
    if(document.getElementById("result") != undefined){
        document.getElementById("result").innerHTML =  "<font color = 'red'>" + allPrice + "</font>" ;
    }
}

function addBut(gid) {
    var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
    var count = parseInt(document.getElementById(gid).value) ;
    allPrice -= accMul(price,count) ;
    count ++ ;
    document.getElementById(gid).value = count ;
    calGoods(gid) ;
}
function subBut(gid) {
    var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
    var count = parseInt(document.getElementById(gid).value) ;
    allPrice -= accMul(price,count) ;
    if(count != 0){
        count -- ;
    }
    document.getElementById(gid).value = count ;
    calGoods(gid);
}

function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}
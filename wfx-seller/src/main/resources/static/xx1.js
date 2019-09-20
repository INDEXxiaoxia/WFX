function ShowMsgRightBox() {
    // $("#MsgRightBox").show(1000,"linear");
    $("#MsgRightBox").fadeIn(1000);
}
function CloseMsgRightBox() {
    // $("#MsgRightBox").hide(1000,"linear");
    $("#MsgRightBox").fadeOut(1000);

}

$(document).ready(function(){
    $("#myTip").mouseover(function(event){
        var tooltipHtml = "<div id='tooltip' class='tooltip'>提示信息提示信息！</div>";
        $(this).append(tooltipHtml);
        $("#tooltip").css({
            "top": (event.pageY) + "px",
            "left": ($(this).width()) + "px"  //紧跟在内容的后面
        }).show("fast"); //设置提示框的坐标，并显 示
    }).mouseout(function(){
        $("#tooltip").remove();
    })
}) ;
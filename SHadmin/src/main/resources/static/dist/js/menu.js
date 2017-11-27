// 防止li标签跳转之后丢失active.
$(".sidebar-menu").find("li").each(function () {
    var a = $(this).find("a")[0];
    var index = 0;
    var href = "";
    if ($(a).attr("href") && $(a).attr("href").indexOf("?") != -1) {
        index = $(a).attr("href").indexOf("?");
        href = $(a).attr("href").substr(0, index);
    } else {
        href = $(a).attr("href");
    }
    if (href === location.pathname) {
        $(this).addClass("active");
        $(this).parent().addClass("menu-open").show();
    } else {
        $(this).removeClass("active");
    }
});
// 全局table高亮
$("#table").on("click-row.bs.table", function (e, row, ele) {
    $("tr.info").removeClass("info");
    $(ele).addClass("info");
});
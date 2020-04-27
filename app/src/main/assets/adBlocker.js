window.addEventListener('load', () => {
    console.log('>>>>>>>>>>>congqin load');
//    let ads = document.querySelectorAll('[id^="ad_"]');
//    ads.forEach((node) => {
//        node.remove();
//    });
});

window.addEventListener('DOMContentLoaded', () => {
    console.log('>>>>>>>>>>>congqin DOMContentLoaded');
    let ads = document.querySelectorAll('[id^="ad"], [id^="google_ad"], [class^="ad"]');
    ads.forEach((node) => {
        node.remove();
    });


});
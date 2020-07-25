window.addEventListener('load', () => {
    console.log('>>>>>>>>>>>congqin load');
    let ads = document.querySelectorAll('[id^="ad"], [id^="google_ad"], [class^="ad"]');
    ads.forEach((node) => {
        node.remove();
    });

    let article = document.getElementsByTagName('article');
    if (article[0]) {

        if (article[0].previousElementSibling) {
            article[0].previousElementSibling.remove();
        }
        if (article[0].nextElementSibling) {
            article[0].nextElementSibling.remove();
        }
    }

});

window.addEventListener('DOMContentLoaded', () => {
    console.log('>>>>>>>>>>>congqin DOMContentLoaded');
    let ads = document.querySelectorAll('[id^="ad"], [id^="google_ad"], [class^="ad"]');
    ads.forEach((node) => {
        node.remove();
    });

    let article = document.getElementsByTagName('article');
    if (article[0]) {

        if (article[0].previousElementSibling) {
            article[0].previousElementSibling.remove();
        }
        if (article[0].nextElementSibling) {
            article[0].nextElementSibling.remove();
        }
    }


});
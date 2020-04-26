window.addEventListener('load',() => {
console.log('>>>>>>>>>>>congqin load');
let ads = document.querySelectorAll('[id^="ad_"]');
ads.forEach((node) => {node.remove();});
});

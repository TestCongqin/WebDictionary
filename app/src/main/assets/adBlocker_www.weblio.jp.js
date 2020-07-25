window.addEventListener('load', () => {
    console.log('>>>>>>>>>>>congqin load');

    let iframes = document.querySelectorAll('iframe');
    iframes.forEach(i => {
        i.remove();
    });

    // remove footer
    let footer = document.getElementById('footer');
    if (footer) {
        footer.remove();
    }

    let divs = document.querySelectorAll('body > div');
    divs.forEach(i => {
        if (i.id != 'wrapper') {
            i.remove();
        } else {
            let division2 = i.querySelectorAll('div.division2');
            var ssdjjExist = false;
            division2.forEach(i => {
                if (i.querySelectorAll('a[name=SSDJJ]').length > 0) {
                    ssdjjExist = true;
                }
            });
            if (ssdjjExist) {
                let division2 = i.querySelectorAll('div.division2');
                division2.forEach(i => {
                    if (i.querySelectorAll('a[name=SSDJJ]').length == 0) {
                        i.remove();
                    }
                })
            }

            let division3 = i.querySelectorAll('div.division3');
            division3.forEach(i => { i.remove(); });
        }
    });
});

window.addEventListener('DOMContentLoaded', () => {
    console.log('>>>>>>>>>>>congqin DOMContentLoaded');
    // remove <iframe>
    let iframes = document.querySelectorAll('iframe,script');
    iframes.forEach(i => {
        i.remove();
    });

    // remove footer
    let footer = document.getElementById('footer');
    if (footer) {
        footer.remove();
    }

    let divs = document.querySelectorAll('body > div');
    divs.forEach(i => {
        if (i.id != 'wrapper') {
            i.remove();
        } else {
            let division2 = i.querySelectorAll('div.division2');
            var ssdjjExist = false;
            division2.forEach(i => {
                if (i.querySelectorAll('a[name=SSDJJ]').length > 0) {
                    ssdjjExist = true;
                }
            });
            if (ssdjjExist) {
                let division2 = i.querySelectorAll('div.division2');
                division2.forEach(i => {
                    if (i.querySelectorAll('a[name=SSDJJ]').length == 0) {
                        i.remove();
                    }
                })
            }

            let division3 = i.querySelectorAll('div.division3');
            division3.forEach(i => { i.remove(); });
        }
    });
});
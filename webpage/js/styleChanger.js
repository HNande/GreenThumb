function applyStylesheet() {
    const link = document.getElementById('style');
    const width = window.innerWidth;

    if (width < 600) {
        link.href = "../css/styleP.css";
    } else if (width < 1024) {
        link.href = "../css/styleIPAD.css";
    } else {
        link.href = "../css/stylePC.css";
    }

}

applyStylesheet();

window.addEventListener('resize', applyStylesheet);
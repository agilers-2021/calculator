const API_URL = "";

function init() {
    let $form = document.getElementById("eval-form");
    let $expr = document.getElementById("expr");
    let $result = document.getElementById("result");

    $form.addEventListener("submit", async (e) => {
        let expr = $expr.value;
        e.preventDefault();
        let result = await fetch(API_URL + "/eval", {
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "body": JSON.stringify({
                "str": expr
            })
        });

        let ans = await result.text();
        $result.innerText = ans;
    });
}

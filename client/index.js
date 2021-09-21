const API_URL = "";

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

async function refreshHistory() {
    let $historyEntries = document.getElementById("history-entries")

    let request = await fetch(API_URL + "/history?n=100", {
        "method": "GET",
        "headers": {
            "Content-Type": "application/json"
        }
    });
    let text = await request.text();
    console.log(text)
    let history = JSON.parse(text);

    removeAllChildNodes($historyEntries);

    Array.from(history.entries).reverse().forEach(function (entry) {
        let li = document.createElement("li");
        li.appendChild(document.createTextNode(entry.expression + " = " + entry.result.toString()));
        $historyEntries.appendChild(li);
    })
}

function init() {
    refreshHistory()

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

        await refreshHistory();
    });

    Array.from(document.getElementsByClassName("symbol-button")).forEach(function (value) {
        value.addEventListener("click", function () {
            let text = $expr.value
            let selectionStart = $expr.selectionStart;
            let selectionEnd = $expr.selectionEnd;
            let insertion = value.innerText;
            $expr.value = text.slice(0, selectionStart) + insertion + text.slice(selectionEnd);
            $expr.focus();
            $expr.selectionStart = $expr.selectionEnd = selectionStart + insertion.length;
        })
    })

    document.getElementById("delete-all").addEventListener("click", function () {
        $expr.value = "";
        $expr.focus();
    })

    document.getElementById("delete-last").addEventListener("click", function () {
        let text = $expr.value;
        let selectionEnd = $expr.selectionEnd;
        let selectionStart = Math.min($expr.selectionStart, $expr.selectionEnd - 1);
        $expr.value = text.slice(0, selectionStart) + text.slice(selectionEnd);
        $expr.focus();
        $expr.selectionStart = $expr.selectionEnd = selectionStart;
    })
}

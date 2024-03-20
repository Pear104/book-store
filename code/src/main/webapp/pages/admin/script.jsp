<script>


function showMessageBox() {
    var status = document.getElementById("status");
    var messageBox = document.getElementById("message");

    if (status.value === "CANCELLED") {
    	messageBox.style.display = "block";
    } else {
    	messageBox.style.display = "none";
    }
}

showMessageBox();

console.log("Ahihi");

</script>
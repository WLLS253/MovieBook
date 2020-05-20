var selectedRow = null;

function onFormSubmit() {
    if (validate()) {
        var formData = readFormData();
        if (selectedRow == null) {
            insertNewRecord(formData);
        }
        else {
            updateRecord(formData);
        }
        resetForm();
    }
}

function readFormData() {
    var formData = {};
    formData["username"] = document.getElementById("username").value;
    formData["password"] = document.getElementById("password").value;
    formData["usertype"] = document.getElementById("usertype").value;
    formData["date"] = new Date(); //document.getElementById("date").value;
    formData["status"] = document.getElementById("status").value;
    return formData;
}

function insertNewRecord(data) {
    var table = document.getElementById("userList").getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.length);
    cell1 = newRow.insertCell(0);
    cell1.innerHTML = ""
    cell2 = newRow.insertCell(1);
    cell2.innerHTML = data.username;
    cell3 = newRow.insertCell(2);
    cell3.innerHTML = data.password;
    cell4 = newRow.insertCell(3);
    cell4.innerHTML = data.usertype;
    cell5 = newRow.insertCell(4);
    cell5.innerHTML = data.date;
    cell6 = newRow.insertCell(5);
    cell6.innerHTML = data.status;
    cell8 = newRow.insertCell(6);
    cell8.innerHTML = '<div class="table-data-feature">\
                            <button class="item" data-toggle="tooltip" data-placement="top" onclick="onEdit(this)" title="编辑">\
                                <i class="zmdi zmdi-edit"></i>\
                            </button>\
                            <button class="item" data-toggle="tooltip" data-placement="top" onclick="onDelete(this)" title="删除">\
                                <i class="zmdi zmdi-delete"></i>\
                            </button>\
                        </div>'
    // cell8.innerHTML = '<div class="table-data-feature"><button class="item" data-toggle="tooltip" data-placement="top" onClick="onEdit(this)" title="编辑"><i class="zmdi zmdi-edit"></i></button>
    //                 +'<button class="item" data-toggle="tooltip"data-placement="top"  onClick="onDelete(this)" title="删除"><i class="zmdi zmdi-delete"></i></button></div> ';
}

function resetForm() {
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    document.getElementById("usertype").value = "";
    document.getElementById("date").value = "";
    document.getElementById("status").value = "";
    selectedRow = null;
}

function onEdit(td) {
    selectedRow = td.parentElement.parentElement.parentElement;
    document.getElementById("username").value = selectedRow.cells[1].innerHTML;
    document.getElementById("password").value = selectedRow.cells[2].innerHTML;
    document.getElementById("usertype").value = selectedRow.cells[3].innerHTML;
    document.getElementById("date").value = selectedRow.cells[4].innerHTML;
    document.getElementById("status").value = selectedRow.cells[5].innerHTML;
}

function updateRecord(formData) {
    selectedRow.cells[1].innerHTML = formData.username;
    selectedRow.cells[2].innerHTML = formData.password;
    selectedRow.cells[3].innerHTML = formData.usertype;
    selectedRow.cells[4].innerHTML = formData.date;
    selectedRow.cells[5].innerHTML = formData.status;
}

function onDelete(td) {
    if (confirm('Are you sure to delete this record?')) {
        row = td.parentElement.parentElement.parentElement;
        document.getElementById("userList").deleteRow(row.rowIndex);
        resetForm();
    }
}

function validate() {
    isValid = true;
    if (document.getElementById("username").value == "") {
        isValid = false;
        alert("请输入用户名")
    }
    else if(document.getElementById("password").value == ""){
        isValid = false;
        alert("请输入密码")
    }
    else if(document.getElementById("usertype").value == ""){
        isValid = false;
        alert("请选择用户类型")
    }
    else {
        isValid = true;
    }
    return isValid;
}
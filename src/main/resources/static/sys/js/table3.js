var selectedRow = null;

function onFormSubmit() {
    var formData = readFormData();
    if (selectedRow == null){
        insertNewRecord(formData);
    }
    else{
        updateRecord(formData);
    }
    resetForm();
}

function readFormData() {
    var formData = {};
    formData["cinemaname"] = document.getElementById("cinemaname").value;
    formData["location"] = document.getElementById("location").value;
    formData["grade"] = document.getElementById("grade").value;
    formData["phone"] = document.getElementById("phone").value; //document.getElementById("phone").value;
    formData["cinemadescription"] = document.getElementById("cinemadescription").value;
    return formData;
}

function insertNewRecord(data) {
    var table = document.getElementById("userList").getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.length);
    cell1 = newRow.insertCell(0);
    cell1.innerHTML = ""
    cell2 = newRow.insertCell(1);
    cell2.innerHTML = data.cinemaname;
    cell3 = newRow.insertCell(2);
    cell3.innerHTML = data.location;
    cell4 = newRow.insertCell(3);
    cell4.innerHTML = data.grade;
    cell5 = newRow.insertCell(4);
    cell5.innerHTML = data.phone;
    cell6 = newRow.insertCell(5);
    cell6.innerHTML = data.cinemadescription;
    cell8 = newRow.insertCell(6);
    cell8.innerHTML = '<div class="table-data-feature">\
                            <button class="item" data-toggle="tooltip" data-placement="top" onclick="onEdit(this)" score="编辑">\
                                <i class="zmdi zmdi-edit"></i>\
                            </button>\
                            <button class="item" data-toggle="tooltip" data-placement="top" onclick="onDelete(this)" score="删除">\
                                <i class="zmdi zmdi-delete"></i>\
                            </button>\
                        </div>'
    // cell8.innerHTML = '<div class="table-data-feature"><button class="item" data-toggle="tooltip" data-placement="top" onClick="onEdit(this)" score="编辑"><i class="zmdi zmdi-edit"></i></button>
    //                 +'<button class="item" data-toggle="tooltip"data-placement="top"  onClick="onDelete(this)" score="删除"><i class="zmdi zmdi-delete"></i></button></div> ';
}

function resetForm() {
    document.getElementById("cinemaname").value = "";
    document.getElementById("location").value = "";
    document.getElementById("grade").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("cinemadescription").value = "";
    selectedRow = null;
}

function onEdit(td) {
    selectedRow = td.parentElement.parentElement.parentElement;
    document.getElementById("cinemaname").value = selectedRow.cells[1].innerHTML;
    document.getElementById("location").value = selectedRow.cells[2].innerHTML;
    document.getElementById("grade").value = selectedRow.cells[3].innerHTML;
    document.getElementById("phone").value = selectedRow.cells[4].innerHTML;
    document.getElementById("cinemadescription").value = selectedRow.cells[5].innerHTML;
}

function updateRecord(formData) {
    selectedRow.cells[1].innerHTML = formData.cinemaname;
    selectedRow.cells[2].innerHTML = formData.location;
    selectedRow.cells[3].innerHTML = formData.grade;
    selectedRow.cells[4].innerHTML = formData.phone;
    selectedRow.cells[5].innerHTML = formData.cinemadescription;
}

function onDelete(td) {
    if (confirm('Are you sure to delete this record?')) {
        row = td.parentElement.parentElement.parentElement;
        document.getElementById("userList").deleteRow(row.rowIndex);
        resetForm();
    }
}
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
    formData["moviename"] = document.getElementById("moviename").value;
    formData["movietype"] = document.getElementById("movietype").value;
    formData["area"] = document.getElementById("area").value;
    formData["moviedescription"] = document.getElementById("moviedescription").value; //document.getElementById("moviedescription").value;
    formData["image"] = document.getElementById("image").value;
    return formData;
}

function insertNewRecord(data) {
    var table = document.getElementById("userList").getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.length);
    cell1 = newRow.insertCell(0);
    cell1.innerHTML = ""
    cell2 = newRow.insertCell(1);
    cell2.innerHTML = data.moviename;
    cell3 = newRow.insertCell(2);
    cell3.innerHTML = data.movietype;
    cell4 = newRow.insertCell(3);
    cell4.innerHTML = data.area;
    cell5 = newRow.insertCell(4);
    cell5.innerHTML = data.moviedescription;
    cell6 = newRow.insertCell(5);
    cell6.innerHTML = data.image;
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
    document.getElementById("moviename").value = "";
    document.getElementById("movietype").value = "";
    document.getElementById("area").value = "";
    document.getElementById("moviedescription").value = "";
    document.getElementById("image").value = "";
    selectedRow = null;
}

function onEdit(td) {
    selectedRow = td.parentElement.parentElement.parentElement;
    document.getElementById("moviename").value = selectedRow.cells[1].innerHTML;
    document.getElementById("movietype").value = selectedRow.cells[2].innerHTML;
    document.getElementById("area").value = selectedRow.cells[3].innerHTML;
    document.getElementById("moviedescription").value = selectedRow.cells[4].innerHTML;
    document.getElementById("image").value = selectedRow.cells[5].innerHTML;
}

function updateRecord(formData) {
    selectedRow.cells[1].innerHTML = formData.moviename;
    selectedRow.cells[2].innerHTML = formData.movietype;
    selectedRow.cells[3].innerHTML = formData.area;
    selectedRow.cells[4].innerHTML = formData.moviedescription;
    selectedRow.cells[5].innerHTML = formData.image;
}

function onDelete(td) {
    if (confirm('Are you sure to delete this record?')) {
        row = td.parentElement.parentElement.parentElement;
        document.getElementById("userList").deleteRow(row.rowIndex);
        resetForm();
    }
}
 function createTask() {
    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    console.log(tasks)
    console.log(document.getElementById("Nome").value)
    console.log(document.getElementById("Descricao").value)
    console.log(document.getElementById("Data_entrega").value)
    console.log(document.getElementById("Prioridade").value)
    console.log(document.getElementById("Categoria").value)
    console.log(document.getElementById("Status").value)
    tasks.push({"nome": document.getElementById("Nome").value,
         "descricao": document.getElementById("Descricao").value,
          "data_entrega": document.getElementById("Data_entrega").value,
           "prioridade":document.getElementById("Prioridade").value,
            "categoria": document.getElementById("Categoria").value,
             "status": document.getElementById("Status").value, select: true})
    localStorage.setItem('tasks', JSON.stringify(tasks));
    console.log(tasks)         
    window.location.href = 'viewTask.html';
}

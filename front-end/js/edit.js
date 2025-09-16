let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
// console.log(tasks)
task = tasks
for (let t in tasks){
    if (tasks[t].edit == true){
        //  console.log(tasks[t])
    document.getElementById("Nome").value =tasks[t].nome
    document.getElementById("Descricao").value =tasks[t].descricao
    document.getElementById("Data_entrega").value =new Date(tasks[t].data_entrega).toISOString().split('T')[0]
    document.getElementById("Prioridade").value =tasks[t].prioridade
    document.getElementById("Categoria").value =tasks[t].categoria
    document.getElementById("Status").value =tasks[t].status
    }
}

function createTask() {
    for (let t in tasks){
        if (tasks[t].edit == true){
    tasks[t].nome = (document.getElementById("Nome").value)
    tasks[t].descricao = (document.getElementById("Descricao").value)
    tasks[t].data_entrega = (document.getElementById("Data_entrega").value)
    tasks[t].prioridade = (document.getElementById("Prioridade").value)
    tasks[t].categoria = (document.getElementById("Categoria").value)
    tasks[t].status = (document.getElementById("Status").value)
    tasks[t].edit = false
        }
    }
    localStorage.setItem('tasks', JSON.stringify(tasks));
    // console.log(tasks)         
    window.location.href = 'viewTask.html';
}
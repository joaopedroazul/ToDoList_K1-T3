document.getElementById('create-button').onclick = function() {
    window.location.href = 'create.html';
}

// task = [
// {"nome": "Estudar Python","descricao": "Completar módulo de funções","data_entrega": "2024-08-30","prioridade":" 4","categoria": "Estudos","status": "todo", "select": false, "edit": false},
// {"nome": "Pagar conta de luz","descricao": "Vencer dia 05/09","data_entrega": "2024-09-04","prioridade":" 5","categoria": "Finanças","status": "todo", "select": false, "edit": false},
// {"nome": "Reunião com equipe","descricao": "Apresentar novos projetos","data_entrega": "2024-08-28","prioridade":" 3","categoria": "Trabalho","status": "doing", "select": false, "edit": false},
// {"nome": "Academia","descricao": "Treino de pernas","data_entrega": "2024-08-27","prioridade":" 2","categoria": "Saúde","status": "done", "select": false, "edit": false},
// {"nome": "Compras do mês","descricao": "Mercado e farmácia","data_entrega": "2024-09-02","prioridade":" 4","categoria": "Casa","status": "todo", "select": false, "edit": false},
// {"nome": "Ler livro","descricao": "Capítulos 5-8 de 'Hábitos Atômicos'","data_entrega": "2024-09-10","prioridade":" 3","categoria": "Pessoal","status": "doing", "select": false, "edit": false},
// {"nome": "Marcar dentista","descricao": "Check-up semestral","data_entrega": "2024-09-15","prioridade":" 2","categoria": "Saúde","status": "todo", "select": false, "edit": false}
// ]

// localStorage.setItem('tasks', JSON.stringify(task));
let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
// console.log(tasks)

if  (tasks.length > 0){
    document.getElementById('show_tasks').innerHTML +='';
    for (let t in tasks){
        dia = new Date(tasks[t].data_entrega).getDay()
        mes = new Date(tasks[t].data_entrega).getMonth()
        ano = new Date(tasks[t].data_entrega).getFullYear()
        document.getElementById('show_tasks').innerHTML +='<div class="card"> <strong> Nome: ' +
        tasks[t].nome +'</strong> </br><strong> Descrição: ' +
        tasks[t].descricao +'</strong></br> <strong> Data_entrega: ' +
        dia+" / "+mes+" / "+ano +'</strong></br> <strong>Prioridade: ' +
        tasks[t].prioridade +'</strong></br> <strong>Categoria:' +
        tasks[t].categoria +'</strong> </br><strong>Status: ' +
        tasks[t].status +'</strong> </br> </br><button class ="edit-task"> Ver mais </button> <button class ="delete-task"> Deletar </button> </div>'
        
    }
    for (let t in tasks){
        document.getElementsByClassName('edit-task')[t].onclick = function() {
            tasks[t].select = true
            localStorage.setItem('tasks', JSON.stringify(tasks));
            console.log(tasks)
            window.location.href = 'viewTask.html';
        }
        document.getElementsByClassName('delete-task')[t].onclick = function() {
            tasks = tasks.slice(0, t).concat(tasks.slice(parseInt(t) + 1, tasks.length))
            localStorage.setItem('tasks', JSON.stringify(tasks));
            console.log(tasks)
            window.location.href = 'index.html';
        }
    }    
}

document.getElementById('task-options').onchange = function() {
    const selectedOption = document.getElementById('task-options').value;
    if (selectedOption == "all"){
        console.log("all")
        for (let t in tasks){
            dia = new Date(tasks[t].data_entrega).getDay()
            mes = new Date(tasks[t].data_entrega).getMonth()
            ano = new Date(tasks[t].data_entrega).getFullYear()
            document.getElementById('show_tasks').innerHTML +='<div class="card"> <strong> Nome: ' +
            tasks[t].nome +'</strong> </br><strong> Descrição: ' +
            tasks[t].descricao +'</strong></br> <strong> Data_entrega: ' +
            dia+" / "+mes+" / "+ano +'</strong></br> <strong>Prioridade: ' +
            tasks[t].prioridade +'</strong></br> <strong>Categoria:' +
            tasks[t].categoria +'</strong> </br><strong>Status: ' +
            tasks[t].status +'</strong> </br> </br><button class ="edit-task"> Ver mais </button> <button class ="delete-task"> Deletar </button> </div>'
        }
    } else {
        document.getElementById('show_tasks').innerHTML = '';
        for (let t in tasks){
            if (tasks[t].status == selectedOption){
                dia = new Date(tasks[t].data_entrega).getDay();
                mes = new Date(tasks[t].data_entrega).getMonth();
                ano = new Date(tasks[t].data_entrega).getFullYear();
                document.getElementById('show_tasks').innerHTML +='<div class="card"> <strong> Nome: ' +
                tasks[t].nome +'</strong> </br><strong> Descrição: ' +
                tasks[t].descricao +'</strong></br> <strong> Data_entrega: ' +
                dia+" / "+mes+" / "+ano +'</strong></br> <strong>Prioridade: ' +
                tasks[t].prioridade +'</strong></br> <strong>Categoria:' +
                tasks[t].categoria +'</strong> </br><strong>Status: ' +
                tasks[t].status +'</strong> </br> </br><button class ="edit-task"> Ver mais </button> <button class ="delete-task"> Deletar </button> </div>'
            }else{
                continue;
            }
        }
    }    


for (let t in tasks){
    document.getElementsByClassName('edit-task')[t].onclick = function() {
        tasks[t].select = true
        localStorage.setItem('tasks', JSON.stringify(tasks));
        console.log(tasks)
        window.location.href = 'viewTask.html';
    }
    document.getElementsByClassName('delete-task')[t].onclick = function() {
        tasks = tasks.slice(0, t).concat(tasks.slice(parseInt(t) + 1, tasks.length))
        localStorage.setItem('tasks', JSON.stringify(tasks));
        console.log(tasks)
        window.location.href = 'index.html';
    }
}


}

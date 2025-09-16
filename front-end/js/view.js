

let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
// console.log(tasks)
task = tasks
for (let t in tasks){
    if (tasks[t].select == true){
        // console.log(tasks[t])
        dia = new Date(tasks[t].data_entrega).getDay()
        mes = new Date(tasks[t].data_entrega).getMonth()
        ano = new Date(tasks[t].data_entrega).getFullYear()
        document.getElementsByClassName("nome")[0].innerHTML =  "Nome: "+tasks[t].nome +"</br>"
        document.getElementsByClassName("descricao")[0].innerHTML =  "Descrição: "+tasks[t].descricao+"</br>"
        document.getElementsByClassName("data_entrada")[0].innerHTML = "Data: "+dia+"/"+mes+"/"+ano+"</br>"
        document.getElementsByClassName("prioridade")[0].innerHTML = "Prioridade: "+tasks[t].prioridade+"</br>"
        document.getElementsByClassName("categoria")[0].innerHTML = "Categoria: "+tasks[t].categoria+"</br>"
        document.getElementsByClassName("status")[0].innerHTML = "Status: "+tasks[t].status+"</br>"
    }
}
for (let t in tasks){
    if(tasks[t].select == true){
        document.getElementById('return').onclick = function() {
            for (let t in tasks){
                if (tasks[t].select == true){
                    tasks[t].select = false
                    localStorage.setItem('tasks', JSON.stringify(tasks));
                    console.log(tasks)
                }
            }
            window.location.href = 'index.html';
        }
    
        document.getElementById('remove-task').onclick = function() {
            tasks = tasks.slice(0, t).concat(tasks.slice(parseInt(t) + 1, tasks.length))
            localStorage.setItem('tasks', JSON.stringify(tasks));
            console.log(tasks)
            window.location.href = 'index.html';
        }
    
        document.getElementById('edit-task').onclick = function() {
            tasks[t].edit = true
            localStorage.setItem('tasks', JSON.stringify(tasks));
            console.log(tasks)
            window.location.href = 'edit.html';
        }
    }
    
}





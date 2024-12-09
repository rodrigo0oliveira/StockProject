window.addEventListener('load',()=>{
    const sse = new EventSource("https://localhost:8843/stock/sse",{
        withCredentials:true
    });
    
    sse.onopen = ()=>{ 
        console.log("OPEN");
    };
    
    sse.addEventListener("Create_Product",(e)=>{
        const data = JSON.parse(e.data);
        const newRow = document.createElement("tr");
        newRow.innerHTML = `
            <td>${data.code}</td>
            <td>${data.name}</td>
            <td>${data.price}</td>
            <td>${data.quantity}</td>
            <td>${data.category}</td>
            <td>${data.status}</td>
        `;

        const body = document.getElementById("body");
        body.appendChild(newRow);
    });
    
    
    sse.onerror = (error) => {
        console.error(error);
    }
    
})
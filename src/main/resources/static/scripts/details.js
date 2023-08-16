// Add SDK credentials
// REPLACE WITH YOUR PUBLIC KEY AVAILABLE IN: https://developers.mercadopago.com/panel


var metaTag = document.getElementsByTagName('meta');

function metaTagData() {
  for (let index = 0; index < metaTag.length; index++) {
    const element = metaTag[index];
    if (element.httpEquiv) {

      console.log(element)
    }
  }
}
var policeContent = document.createElement('meta')

policeContent.httpEquiv = 'Content Security Policy'
policeContent.content = "" +

  "frame-src 'self' https://www.mercadolibre.com.ar https://*.mercadolibre.com http://*.mercadopago.com.ar https://*.mercadopago.com https://www.mercadopago.com.ar;"

// document.head.appendChild(policeContent);
let mpago = {}
var script = document.createElement("script"); // create a script DOM node
script.src = 'https://sdk.mercadopago.com/js/v2'
document.querySelector("#statusData").appendChild(script); // import MercadoPago from 'https://sdk.mercadopago.com/js/v2.js';
script.onload = function () {
  mpago = new MercadoPago('APP_USR-ff96fe80-6866-4888-847e-c69250754d38', {
    locale: 'es-AR',
    advancedFraudPrevention: true
  })

  console.log("script load!")
  metaTagData()

}
/*
/////////
window.onload = function(){
  getData()
}
/////////
function getData(){
  const orderData = {
    id: document.getElementById("id").innerHTML,
    title: document.getElementById("title").innerHTML,
    unit: document.getElementById("unit").innerHTML,
    img: document.getElementById("img").getAttribute("src"),
    // description: "tienda e-comerce",
    price: document.getElementById("price").innerHTML
  };
  console.log("datos recuperados")
  console.log(orderData)
  
  fetch("/store/detail/data", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },

      body: JSON.stringify(orderData),
    })
    .then(reponse => {
      // if (response.redirected) {
      //   // window.location.href=response.url;
      // }
      console.log("respuesta")
      return reponse.json()
    })
    .then(obj => {
      console.log(obj)
      let recivido ={};
      Object.assign(recivido, obj)
      let pref_id = ""
      pref_id = recivido.prefenceId;
      renderBoton(pref_id);
    })
  
}
function renderBoton(pref_id){
  const bricsBuilder =mpago.bricks();
      const butonCool = async (bricsBuilder) =>{
        const settings ={
          initialization: {
            preferenceId: pref_id,
            redirectMode: 'modal'
          },
          customization: {
            tests:{
              
              label:'Pagar la compra'
            }
            
          }
        }
        const botonControl = await bricsBuilder.create("wallet","boton-pago",settings);
      };
      butonCool(bricsBuilder);
}
///////////
*/

let reciveFlag = false
let recivido = {};
const statusData = document.getElementById("statusData");
const garpando = document.getElementById("garpando");
const botton = garpando.addEventListener("click", function () {

  $('#garpando').attr("disabled", false);

  const orderData = {
    id: document.getElementById("id").innerHTML,
    title: document.getElementById("title").innerHTML,
    unit: document.getElementById("unit").innerHTML,
    img: document.getElementById("img").getAttribute("src"),
    // description: "tienda e-comerce",
    price: document.getElementById("price").innerHTML
  };
  console.log("datos recuperados")
  console.log(orderData)
  
  fetch("/store/detail/data", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },

      body: JSON.stringify(orderData),
    })
    .then(reponse => {
      // if (response.redirected) {
      //   // window.location.href=response.url;
      // }
      console.log("respuesta")
      return reponse.json()
    })
    .then(obj => {
      console.log(obj)
      Object.assign(recivido, obj)
      let pref_id = ""
      const recivedo = document.createElement("p")
      const prefenceId = document.createElement("p")
      recivedo.innerText = JSON.stringify(recivido, null, 2)
      prefenceId.innerText = JSON.stringify(recivido.prefenceId);
      pref_id = recivido.prefenceId;
      console.log(pref_id)
      statusData.appendChild(recivedo)
      statusData.appendChild(prefenceId)

      mpago.checkout({
        preference: {
          id: pref_id
        },
      }).open()
      
      

    })
})
/**/
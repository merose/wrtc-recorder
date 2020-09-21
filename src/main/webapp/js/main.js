const connectElement = document.getElementById("connect")
const disconnectElement = document.getElementById("disconnect")
const filenameElement = document.getElementById("filename")
const dataElement = document.getElementById("data")
const sendElement = document.getElementById("send")

connectElement.onclick = connect
disconnectElement.onclick = disconnect
sendElement.onclick = send

let ws

function connect() {
	let host = document.location.host
	let pathname = document.location.pathname
	let filename = filenameElement.value
	ws = new WebSocket("ws://" + host + pathname + "upload/" + filename);
	console.log('Connected')
}

function disconnect() {
	ws.close()
	ws = undefined
	console.log('Disconnected')
}

function send() {
	const content = (new TextEncoder()).encode(dataElement.value)
	ws.send(content)
}
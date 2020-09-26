const statusElement = document.getElementById("status")
const connectElement = document.getElementById("connect")
const disconnectElement = document.getElementById("disconnect")
const filenameElement = document.getElementById("filename")
const dataElement = document.getElementById("data")
const sendElement = document.getElementById("send")
const recordElement = document.getElementById("record")
const stopElement = document.getElementById("stop")
const playElement = document.getElementById("play")
const audioElement = document.getElementById("audio")

connectElement.onclick = connect
disconnectElement.onclick = disconnect
sendElement.onclick = send
recordElement.onclick = start
stopElement.onclick = stop
playElement.onclick = play


const constraints = {
	audio: {
		channelCount: 1,
		sampleRate: 44100,
		sampleSize: 2
	},
	video: false
}

let ws
let mediaRecorder

function connect() {
	let host = document.location.host
	let pathname = document.location.pathname
	let filename = filenameElement.value
	ws = new WebSocket("ws://" + host + pathname + "upload/" + filename);
	ws.onerror = onError
	console.log('Connected')
	statusElement.innerHTML = "connected"
}

function disconnect() {
	ws.close()
	ws = undefined
	console.log('Disconnected')
	statusElement.innerHTML = "not connected"
}

function send() {
	const content = (new TextEncoder()).encode(dataElement.value)
	ws.send(content)
}

function start() {
	mediaRecorder.ondataavailable = onChunk
	mediaRecorder.start(1000)
	console.log('Started recording')
}

function stop() {
	mediaRecorder.stop()
	console.log('Stopped recording')
}

function onChunk(event) {
	ws.send(event.data)
	console.log('Sent chunk')
}

function play(event) {
	let host = document.location.host
	let pathname = document.location.pathname
	let filename = filenameElement.value
	audioElement.src = 'recordings/' + filename
	console.log('Playing from ' + audioElement.src)
	audioElement.play()
}

function onError(event) {
	statusElement.innerHTML = "error: " + event
}

navigator.mediaDevices.getUserMedia(constraints).then(stream => {
	mediaRecorder = new MediaRecorder(stream)
})

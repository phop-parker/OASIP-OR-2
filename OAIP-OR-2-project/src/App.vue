<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
// import HelloWorld from './components/HelloWorld.vue'
import { onBeforeMount, ref } from 'vue'
const fullWords = ref([])
//GET
const getFullWords = async () => {
	const res = await fetch('/api/events')
  console.log(`${res.status}`)
	if (res.status === 200) {
		fullWords.value = await res.json()
    console.log(fullWords.value)
	} else {
		console.log('error,cannot get data')
	}
}

onBeforeMount(async () => await getFullWords())
</script>


<template>
hello
{{fullWords}}
</template>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

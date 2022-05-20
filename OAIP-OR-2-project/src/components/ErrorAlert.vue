<!-- @format -->

<script setup>
defineEmits(['backTo'])

const props = defineProps({
  errorTitle: {
    type: Array,
    required: true
  }
})
// const showmodal = true
</script>

<template>
  <div class="absolute bg-black opacity-20 inset-0 z-0"></div>
  <div
    class="box-content mr-auto ml-auto pt-10 pb-14 h-40 w-80 bg-white rounded-2xl drop-shadow-2xl font-Kanit"
  >
    <img
      class="w-16 h-16 -m-1 flex items-center text-red-500 mx-auto"
      src="../assets/failed.png"
      alt=""
    />
    <div class="text-center p-5 flex-auto justify-center">
      <div class="text-center text-blood-bird pt-3 z-40">ERROR ALERT</div>
      <div class="text-center text-blood-bird pt-3 z-40" v-for="(error, index) in errorTitle" :key="index">
        {{ error }}
      </div>
      <div>
        <div class="pt-4 flex gap-4 justify-center">
          <button
            @click="$emit('backTo')"
            class="bg-dark-orange hover:bg-yellow-100 hover:text-dark-orange text-white py-2 px-4 rounded-3xl"
          >
            GO BACK
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<!-- w-full  max-w-lg p-5 relative mx-auto my-auto rounded-xl shadow-lg  bg-white  -->
<style scoped>
.gradient-color {
  background-image: linear-gradient(to top, #ffffff, #fbbf98);
}
</style>

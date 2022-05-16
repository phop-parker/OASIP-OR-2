<script setup>
defineEmits(['yesConfirmation', 'cancelConfirmation'])
</script>
 
<template>
<div class="absolute bg-black opacity-20 inset-0 z-0"></div>

    <div
        class=" box-content mr-auto ml-auto pt-10 pb-0 h-40 w-80 gradient-color rounded-3xl drop-shadow-2xl font-Kanit z-10">
        <div class="text-center text-blood-bird pt-3 z-40">
            Are you sure you want to delete ?
        </div>
        <div class="pt-4 flex gap-4 justify-center  ">
            <button @click="$emit('cancelConfirmation')"
                class="bg-dark-pink hover:bg-yellow-100  hover:text-dark-pink text-white py-2 px-4 rounded-3xl ">
                cancel
            </button>
            <button @click="$emit('yesConfirmation')"
                class=" bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white py-2 px-4  rounded-3xl">
                delete
            </button>
        </div>
    </div>

</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>
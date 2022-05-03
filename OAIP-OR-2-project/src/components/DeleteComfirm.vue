<script setup>
defineEmits(['yesConfirmation', 'noConfirmation'])
const props = defineProps({
    ConfirmationTopic: {
        type: String,
        required: true
    }
})

</script>
 
<template>

    <div class=" mr-auto ml-auto pt-10 h-40 w-80 box-content  bg-pink-50 rounded-3xl drop-shadow-2xl font-Kanit">
        <div class="text-center text-blood-bird">
            Are you sure you want to delete ?
        </div>
        <div class="pt-4 flex gap-4 justify-center  ">
            <button @click="$emit('noConfirmation')"
                class="bg-orange-100 hover:bg-yellow-100 text-blood-bird  py-2 px-4 rounded-3xl">
                cancle
            </button>
            <button @click="$emit('yesConfirmation')"
                class=" bg-orange-100 hover:bg-yellow-100 text-blood-bird py-2 px-4  rounded-3xl">
                delete
            </button>
        </div>
    </div>

</template>
 
<style>
</style>
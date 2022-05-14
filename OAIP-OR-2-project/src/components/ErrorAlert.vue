<script setup>
defineEmits(['ConfirmAndGoToAnotherPage'])

const props = defineProps({
    errorTitle: {
        type: String,
        required: true
    },
})

</script>
 

 
<template>

    <div
        class=" box-content mr-auto ml-auto pt-10 pb-0 h-40 w-80 box-content gradient-color rounded-3xl drop-shadow-2xl font-Kanit">
        <div class="text-center text-blood-bird pt-3 z-40">
            ERROR ALERT
        </div>
        <div class="text-center text-blood-bird pt-3 z-40">
            {{ props.errorTitle }}
        </div>
        <div>
            <div class="pt-4 flex gap-4 justify-center  ">
                <router-link :to="{ name: 'ViewsAllAppointment' }">
                    <button
                        class="bg-dark-orange hover:bg-yellow-100 hover:text-dark-orange text-white py-2 px-4  rounded-3xl">
                        GO BACK
                    </button>
                </router-link>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>
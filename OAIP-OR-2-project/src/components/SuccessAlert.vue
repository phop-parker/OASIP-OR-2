<script setup>

const props = defineProps({
    successTitle: {
        type: String,
        required: true
    },
})

</script>
 

 
<template>

    <div
        class=" box-content mr-auto ml-auto h-40 w-80 gradient-color rounded-3xl drop-shadow-2xl font-Kanit">
        <div class="text-center text-blood-bird  z-40">
            {{ props.successTitle }} success !
        </div>
    </div>

</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>
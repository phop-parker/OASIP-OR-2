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
        class=" box-content p-12 gradient-color rounded-3xl drop-shadow-2xl font-Kanit">
           <div class="text-center  justify-center">
        <div class=" text-blood-bird ">
            {{ props.successTitle }} success !
        </div>
        </div>
    </div>

</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>
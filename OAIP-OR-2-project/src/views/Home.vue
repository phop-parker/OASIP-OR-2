<script setup>

</script>
 
<template>
    <div>
        <img class="w-5/12 h-5/12 mt-14 ml-36  " src="../assets/welcome.png" alt="welcome">
        <div>
            <router-link :to="{ name: 'EventBooking' }">
                <button
                    class=" h-14 w-32 mt-7 ml-60 button text-xl text-blood-bird gradient-color py-2 px-4 rounded-3xl">
                    BOOK
                </button>
            </router-link>
            <router-link :to="{ name: 'ViewsAllAppointment' }">
                <button
                    class="h-14 w-32 mt-7 ml-12 button text-xl text-blood-bird gradient-color py-2 px-4 rounded-3xl">
                    VIEW
                </button>
            </router-link>
        </div>

    </div>

</template>
 
<style>
.gradient-color {
    background-image: linear-gradient(to top, #FC9F5F, #F6E7B2);
}

.button:hover {
    box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
}
</style>
<!-- @format -->

<script setup></script>

<template>
  <div class="grid grid-cols-2">
    <div>
      <div class="flex justify-center mt-20">
        <img class="w-5/6 ml-20" src="../assets/welcome.png" alt="welcome" />
      </div>
      <div class="flex justify-center ml-20 gap-8">
        <router-link :to="{ name: 'EventBooking' }">
          <button
            class="h-14 w-32 button text-xl text-blood-bird gradient-color py-2 px-4 rounded-3xl"
          >
            BOOK
          </button>
        </router-link>
        <router-link :to="{ name: 'ViewsAllAppointment' }">
          <button
            class="h-14 w-32 button text-xl text-blood-bird gradient-color py-2 px-4 rounded-3xl"
          >
            VIEW
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<style>
.gradient-color {
  background-image: linear-gradient(to top, #fc9f5f, #f6e7b2);
}

.button:hover {
  box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24),
    0 17px 50px 0 rgba(0, 0, 0, 0.19);
}
</style>

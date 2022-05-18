<script setup>

</script>
 
<template>
  <nav class="font-Kanit flex items-center justify-between flex-wrap shadow-lg px-2 sm:px-4 py-2.5 rounded m-0  ">
    <div class="container flex flex-wrap justify-between items-center mx-auto">
      <div>
        <img src="../assets/logo.png" class="h-24" />
      </div>

      <div class=" w-full md:block md:w-auto">
        <ul class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium text-center ">
          <li>
            <router-link :to="{ name: 'Home' }">
              <p class="block mt-4 lg:inline-block lg:mt-0 text-blood-bird hover:drop-shadow-lg mr-10">
                HOME
              </p>
            </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'CategoryList' }">
              <p class="block mt-4 lg:inline-block lg:mt-0 text-blood-bird hover:drop-shadow-lg mr-10">
                CATEGORY <br> LIST
              </p>
            </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'EventBooking' }">
              <p class="block mt-4 lg:inline-block lg:mt-0 text-blood-bird hover:drop-shadow-lg mr-10">
                EVENT <br> BOOKING
              </p>
            </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'ViewsAllAppointment' }">
              <p class="block mt-4 lg:inline-block lg:mt-0 text-blood-bird hover:drop-shadow-lg mr-10">
                ALL <br> APPOINTMENTS
              </p>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>


</template>
 
<style>
</style>
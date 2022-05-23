<!-- @format -->

<script setup>
import CategoryDetails from './CategoryDetails.vue'
import { ref, computed } from 'vue'
defineEmits(['deleteEvent', 'updateThisCategory'])

const props = defineProps({
  categories: {
    type: Array,
    default: []
  }
})

const selectedCategory = ref({})
const statusDetail = ref(false)

const getCategorySelected = async(selectedCategoryId) =>{
  const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCategories/${selectedCategoryId}`)
  // const res = await fetch(`http://10.4.56.95:8080/api/eventCategories/${selectedCategoryId}`)
  if (res.status === 200) {
    selectedCategory.value = await res.json();
    toggleStatus()
  } else { console.log("cannot get selected Category")}
}

const toggleStatus = () => 
    statusDetail.value === false
    ? (statusDetail.value = true)
    : (statusDetail.value = false)

const toggleTofalse = () => {
  statusDetail.value = false
}

const updatedCategory = ref()

const getUpdateCategory = (updateCategory) => {
  updatedCategory.value = updateCategory
  return updateCategory
}

</script>

<template>
  <div
    v-if="statusDetail"
    class="absolute inset-0 flex justify-center items-center z-20"
  >
    <CategoryDetails
      :category="selectedCategory"
      @closePopUp="toggleStatus()"
      @getEditedCategory="getUpdateCategory"
      @updateCategory="
        $emit('updateThisCategory', updatedCategory), toggleTofalse()
      "
    />
  </div>
  <div class="py-16 overflow-hidden">
    <div class="container m-auto px-6 space-y-8 text-gray-500 md:px-12">
      <div>
        <h2
          class="mt-4 text-center text-2xl text-blood-bird font-bold md:text-4xl"
        >
          Categories
        </h2>
      </div>
      <div
        class="mt-16 grid border divide-x divide-y rounded-xl overflow-hidden sm:grid-cols-2 lg:divide-y-0 lg:grid-cols-2 xl:grid-cols-5"
      >
        <div v-for="(category, index) in categories" :key="index">
          <div
            class="relative group bg-white transition hover:z-[1] hover:shadow-xl"
          >
            <div class="relative p-8 space-y-8">
              <img
                src="../assets/logo.png"
                class="w-10"
                width="512"
                height="512"
                alt="burger illustration"
              />
              <div class="space-y-2">
                <h5
                  class="text-xl truncate ... text-gray-800 font-medium transition group-hover:text-yellow-600"
                >
                  {{ category.eventCategoryName }}
                </h5>
                <p class="text-sm text-gray-600">
                  Duration : {{ category.eventDuration }} minutes
                </p>
              </div>
              <a
                @click="getCategorySelected(category.categoryId)"
                class="flex justify-between items-center group-hover:text-yellow-600"
              >
                <span class="text-sm">Details</span>
                <span
                  class="-translate-x-4 opacity-0 text-2xl transition duration-300 group-hover:opacity-100 group-hover:translate-x-0"
                  >&RightArrow;</span
                >
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>

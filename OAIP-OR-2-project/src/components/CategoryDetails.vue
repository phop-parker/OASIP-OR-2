<!-- @format -->

<script setup>
import { ref, computed } from 'vue'
import CloseIcon from './CloseIcon.vue'

defineEmits(['closePopUp', 'updateCategory', 'getEditedCategory'])

const props = defineProps({
  category: {
    type: Object,
    default: {}
  }
})

const newCategory = computed(() => {
  return {
    categoryId: props.category.categoryId,
    eventCategoryName: props.category.eventCategoryName,
    eventDuration: props.category.eventDuration,
    eventCategoryDescription: props.category.eventCategoryDescription
  }
})

const editMode = ref(false)

const toggleEditMode = () => {
  if (editMode.value) {
    editMode.value = false
  } else {
    editMode.value = true
  }
}

const nullError = computed(() => {
  if (newCategory.value.eventCategoryName.length <= 0) {
    return true
  } else {
    return false
  }
})
</script>

<template>
  <div class="min-h-screen py-6 flex flex-col justify-center sm:py-12">
    <div class="py-3 sm:max-w-xl sm:mx-auto">
      <div
        class="box-content p-8 pb-8 rounded-3xl bg-pastel-orange bg-opacity-90 drop-shadow-3xl font-Kanit"
      >
        <div class="bg-white w-96 p-4 flex flex-col rounded-xl shadow-lg">
          <div class="place-self-end">
            <CloseIcon
              class="hover:drop-shadow-5xl"
              @click="$emit('closePopUp'), (editMode = false)"
            />
          </div>
          <div class="px-12 py-5">
            <h2 class="text-blood-bird text-3xl text-center font-semibold">
              {{ category.eventCategoryName }}
            </h2>
          </div>

          <div class="inline flex">
            Clinic Name :
            <div v-if="editMode == false">
              {{ category.eventCategoryName }}
              <span v-if="nullError" class="text-red">please insert name</span>
            </div>
            <div v-else>
              <input
                type="text"
                class="shadow"
                v-model="newCategory.eventCategoryName"
              />
            </div>
          </div>
          <div class="inline flex">
            Clinic Duration :
            <div v-if="editMode == false">
              {{ category.eventDuration }} minutes
            </div>
            <div v-else>
              <input
                type="number"
                min="1"
                max="480"
                class="shadow"
                v-model="newCategory.eventDuration"
              />
              minutes
            </div>
          </div>
          <div>
            Clinic Description :
            <div v-if="editMode == false">
              {{ category.eventCategoryDescription }}
            </div>
            <div v-else>
              <textarea
                class="shadow resize-none rounded-md w-full"
                v-model="newCategory.eventCategoryDescription"
                rows="5"
              ></textarea>
            </div>
          </div>
          <div class="w-full flex flex-col items-center">
            <div v-if="editMode == true" class="inline flex mt-4">
              <button
                :disabled="nullError"
                @click="
                  $emit('getEditedCategory', newCategory),
                    $emit('updateCategory'),
                    toggleEditMode()
                "
                class="bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
              >
                summit
              </button>
              <button
                v-if="editMode == true"
                @click="toggleEditMode()"
                class="bg-dark-pink hover:bg-yellow-100 hover:text-dark-pink text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
              >
                cancel
              </button>
            </div>
            <button
              v-else
              @click="toggleEditMode()"
              class="bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2 mt-4"
            >
              edit
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>

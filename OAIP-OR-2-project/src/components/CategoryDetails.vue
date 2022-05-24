<!-- @format -->
<script setup>
import { ref, computed } from 'vue'
import CloseIcon from '../icon/CloseIcon.vue'

defineEmits(['closePopUp', 'updateCategory', 'getEditedCategory'])

const props = defineProps({
  category: {
    type: Object,
    default: {}
  }
})


const categoryId = ref(props.category.categoryId)
const eventCategoryName  = ref(props.category.eventCategoryName)
const eventDuration  = ref(props.category.eventDuration)
const eventCategoryDescription  = ref(props.category.eventCategoryDescription)

const newCategory = computed(() => {
  return {
    categoryId: categoryId.value,
    eventCategoryName: eventCategoryName.value,
    eventDuration: eventDuration.value,
    eventCategoryDescription: eventCategoryDescription.value
  }
})

function clearAll(){
  categoryId.value = props.category.categoryId
  eventCategoryName.value  = props.category.eventCategoryName
  eventDuration.value  = props.category.eventDuration
  eventCategoryDescription.value  = props.category.eventCategoryDescription
}

const editMode = ref(false)

const toggleEditMode = () => 
  editMode.value === false
    ? (editMode.value = true)
    : (editMode.value = false)

// const nullError = computed(() => {
//   if (newCategory.value.eventCategoryName.length <= 0 || newCategory.eventDuration > 480 || newCategory.eventDuration < 1) {
//     return true
//   } else {
//     return false
//   }
// })



</script>

<template>
  <div class="absolute bg-black opacity-20 inset-0 z-0"></div>
  <div class="min-h-screen py-6 flex flex-col justify-center sm:py-12 z-10">
    <div class="py-3 sm:max-w-xl sm:mx-auto">
      <div
        class="box-content detail-box p-8 pb-8 rounded-3xl bg-pastel-yellow bg-opacity-90 drop-shadow-3xl font-Kanit"
      >
        <div class="bg-white detail-box p-4 flex flex-col rounded-xl shadow-lg">
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

          <div class="inline-flex ">
            <div class="font-bold"> Clinic Name<span v-show="eventCategoryName.length<=0" class="text-red-500 ">*</span> : </div>
            <div v-if="editMode == false">
              {{ category.eventCategoryName }}
            </div>
            <div v-else>
              <input
                type="text"
                                v-bind:style="
              eventCategoryName > 100 || eventCategoryName <= 0
              ? 'border: 2px solid rgb(208, 106, 106)'
              : 'border: 2px solid currentcolor'
          "
                v-model="eventCategoryName"
              /> 
            </div>
          </div>
            <span v-show="eventCategoryName.length>100" class="text-red-500 text-xs">category name is too long (100 char only)</span>

          <div class="inline-flex ">
            <div class="font-bold"> Clinic Duration<span v-show=" eventDuration<1 || eventDuration>480" class="text-red-500 ">*</span> :</div>
            <div v-if="editMode == false">
              {{ category.eventDuration }} minutes
            </div>
            <div v-else>
              <input
                type="number"
                minlength="1"
                max="480"
                v-bind:style="
            eventDuration<1 || eventDuration>480
              ? 'border: 2px solid rgb(208, 106, 106)'
              : 'border: 2px solid currentcolor'
          "
                class="shadow"
                v-model="eventDuration"
              />
              minutes
            </div>

          </div>
          <span v-show="eventDuration<1 || eventDuration>480" class="text-red-500 text-xs">event duration can only between 1 -480 minutes</span>
          <div>
            <div class="font-bold	 ">Clinic Description:</div>
            <div v-if="editMode == false">
            <span v-if="category.eventCategoryDescription == null">no clinic description</span>
              {{ category.eventCategoryDescription }}
            </div>
            <div v-else>
              <textarea
                class="shadow resize-none rounded-md w-full"
                v-model="eventCategoryDescription"
                rows="5"
              ></textarea>
             <div v-if="eventCategoryDescription!=null">
                <span v-show="eventCategoryDescription.length > 500" class="text-red-500">
          Description cannot longer than 500 charaters</span
        ></div>
            </div>

          </div>
          <div class="w-full flex flex-col items-center">
            <div v-if="editMode == true" class="inline-flex mt-4">
              <button
                :disabled="eventCategoryName.length <= 0 || eventDuration > 480 || eventDuration < 1"
                @click="
                  $emit('getEditedCategory', newCategory),
                    $emit('updateCategory'),
                    toggleEditMode()
                "
                class="bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
              >
                submit
              </button>
              <button
                v-if="editMode == true"
                @click="toggleEditMode(),clearAll()"
                class="bg-dark-pink hover:bg-yellow-100 hover:text-dark-pink text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2"
              >
                cancel
              </button>
            </div>
            <button
              v-else
              @click="toggleEditMode()"
              class=" bg-dark-green hover:bg-yellow-100 hover:text-dark-green text-white focus:outline-none font-semibold rounded-full text-base px-5 py-2.5 text-center mr-2 mb-2 mt-16 "
            >
              edit
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.detail-box {
  height: 470px;
  width: 500px;
}

</style>

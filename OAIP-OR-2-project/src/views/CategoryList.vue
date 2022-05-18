<!-- @format -->

<script setup>
import { onBeforeMount, ref, computed } from 'vue'
import CategoriesList from '../components/CategoriesList.vue'
import SuccessAlert from '../components/SuccessAlert.vue'
import ErrorAlert from '../components/ErrorAlert.vue'
let categories = ref([])

// GET
const getCategories = async () => {
  const res = await fetch(`${import.meta.env.BASE_URL}/api/eventCategories`)
  // const res = await fetch(`http://10.4.56.95:8080/api/eventCategories`)
  if (res.status === 200) {
    console.log(res)
    categories.value = await res.json()
  } else console.log("error, cann't get data")
}
onBeforeMount(async () => {
  await getCategories()
})
const successStatus = ref(false)
const successDesc = ref('')
const errorStatus = ref(false)
const errorDesc = ref([])

const successToggle = () =>
  successStatus.value === false
    ? (successStatus.value = true)
    : (successStatus.value = false)

const updateCategory = async (updateCategory) => {
  console.log('updateCategory')
  console.log(updateCategory)
  if (
    checkCategoryName(
      updateCategory.eventCategoryName,
      updateCategory.categoryId
    ) &&
    checkEventDuration(updateCategory.eventDuration)
  ) {
    console.log('do patch')
    const res = await fetch(
      `${import.meta.env.BASE_URL}/api/events/${updateEvent.id}`,
      {
        // const res = await fetch(
        //   `http://10.4.56.95:8080/api/eventCategories/${updateCategory.categoryId}`,
        //   {
        method: 'PATCH',
        headers: {
          'content-type': 'application/json'
        },
        body: JSON.stringify({
          eventCategoryName: updateCategory.eventCategoryName,
          eventDuration: updateCategory.eventDuration,
          eventCategoryDescription: updateCategory.eventCategoryDescription
        })
      }
    )
    if (res.status === 200) {
      const modifyCategory = await res.json()
      categories.value = categories.value.map((category) =>
        category.categoryId === modifyCategory.categoryId
          ? {
              ...category,
              eventCategoryName: modifyCategory.eventCategoryName,
              eventDuration: modifyCategory.eventDuration,
              eventCategoryDescription: modifyCategory.eventCategoryDescription
            }
          : category
      )
      console.log('updated suceccfully')
      successStatus.value = true
      successDesc.value = 'Update'
      setTimeout(successToggle, 2000)
    } else {
      console.log('error cannot add')
      alert('error cannot add')
    }
  } else {
    errorStatus.value = true
  }
}

const checkCategoryName = (newCategoryName, categoryId) => {
  console.log('do check')
  console.log('newCategoryName')
  console.log(newCategoryName)
  console.log('categoryId')
  console.log(categoryId)
  const status = ref(true)
  for (let i = 0; i < categories.value.length; i++) {
    console.log(categories.value[i].categoryId)
    if (categories.value[i].categoryId != categoryId)
      console.log('categoryId match')
    if (categories.value[i].eventCategoryName == newCategoryName) {
      console.log('category == categoryName')
      status.value = false
      errorDesc.value.push("Category Name can't be same with other categories")
    }
  }
  return status.value
}

const checkEventDuration = (neweventDuration) => {
  const status = ref(true)
  if (neweventDuration > 1 && neweventDuration < 480) {
    status.value = true
  } else {
    errorDesc.value.push("Category Name can't be same with other categories")
    status.value = false
  }
  return status.value
}

const toggleErrorStatus = () => {
  return (errorStatus.value = false)
}
</script>

<template>
  <div>
    <CategoriesList
      :categories="categories"
      @updateThisCategory="updateCategory"
    />

    <div
      v-if="successStatus == true"
      class="absolute inset-0 flex justify-center items-center z-20"
    >
      <SuccessAlert :successTitle="successDesc" />
    </div>
    <div
      v-if="errorStatus == true"
      class="absolute inset-0 flex justify-center items-center z-20"
    >
      <ErrorAlert :errorTitle="errorDetail" @backTo="toggleErrorStatus" />
    </div>
  </div>
</template>

<style scoped></style>

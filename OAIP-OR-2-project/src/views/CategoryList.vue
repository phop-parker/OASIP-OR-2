<!-- @format -->

<script setup>
import { onBeforeMount, ref, computed } from 'vue'
import CategoriesList from '../components/CategoriesList.vue'
import SuccessAlert from '../components/SuccessAlert.vue'
import ErrorAlert from '../components/ErrorAlert.vue'

const categories = ref([])
const successStatus = ref(false)
const successDesc = ref('')
const errorStatus = ref(false)
const errorDesc = ref([])

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

const successToggle = () =>
  successStatus.value === false
    ? (successStatus.value = true)
    : (successStatus.value = false)

const updateCategory = async (updateCategory) => {
  errorDesc.value = [];
  if (
    checkCategoryName(
      updateCategory.eventCategoryName,
      updateCategory.categoryId
    ) &&
    checkEventDuration(updateCategory.eventDuration
    ) && 
    checkCategoryDescription(updateCategory.eventCategoryDescription)
  ) {
    console.log('do patch')
    const res = await fetch(
      `${import.meta.env.BASE_URL}/api/eventCategories/${updateCategory.categoryId}`,
      {
        // const res = await fetch(
          // `http://10.4.56.95:8080/api/eventCategories/${updateCategory.categoryId}`,
          // {
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
  const status = ref(true)
  if (newCategoryName.length > 100) {
    errorDesc.value.push("Category name should be less than 100 character")

    return false
  } 
  for (let i = 0; i < categories.value.length; i++) {
    console.log(categories.value[i].categoryId)
    if (categories.value[i].categoryId != categoryId){
    if (categories.value[i].eventCategoryName == newCategoryName) {
      console.log('category == categoryName')
      status.value = false
      errorDesc.value.push("Category Name can't be same with other categories")
    }}
  }
  return status.value
}

const checkEventDuration = (neweventDuration) => {
  const status = ref(true)
  if (neweventDuration > 1 && neweventDuration < 480) {
    status.value = true
  } else {
    errorDesc.value.push("event duration should be less than 480 and morethan 1 character")
    status.value = false
  }
  return status.value
}

const toggleErrorStatus = () => {
  return (errorStatus.value = false)
}

const checkCategoryDescription = (newCategoryDescription) => {
  const status = ref(true)
  if (newCategoryDescription.length > 500) {
    errorDesc.value.push("Category Description should be less than 500 characters")
    status.value = false
  } else {
    status.value = true
  }
  return status.value
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
      <ErrorAlert :errorTitle="errorDesc" @backTo="toggleErrorStatus" />
    </div>
  </div>
</template>

<style scoped></style>

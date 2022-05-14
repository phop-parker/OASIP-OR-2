<script setup>
import CloseIcon from "./CloseIcon.vue";
import { ref, computed } from 'vue';


defineEmits(['closePopUp', 'deleteEvent', 'updateEvent', 'getEditedEvent'])

const props = defineProps({
    event: {
        type: Object,
        default: {}
    }
})

const editMode = ref(false)

const getDate = (dateTime) => {
    const date = new Date(dateTime);
    return date.toDateString();
}
const getTime = (dateTime) => {
    const date = new Date(dateTime);
    return date.toTimeString().slice(0, 5)
}


const newEvent = computed(() => {
    return {
        id: props.event.id,
        eventStartTime: props.event.eventStartTime,
        eventNotes: props.event.eventNotes
    }
})

const toggleEditMode = () => {
    if (editMode.value) {
        editMode.value = false;
    } else {
        editMode.value = true;

    }
}

</script>
<template>

    <div class="box-content p-8 pb-8 rounded-3xl gradient-color drop-shadow-3xl font-Kanit  ">
        <div class="grid grid-rows-8 gap-4  bg-white rounded-2xl p-7 pb-10 ">
            <div class="place-self-end ">
                <CloseIcon class="hover:drop-shadow-5xl " @click="$emit('closePopUp'), editMode = false" />
            </div>
            <div class=" text-center text-black ">
                Event Details
            </div>
            <div class=" pl-3 pb-4 drop-shadow-xl">
                name : {{ event.bookingName }}
            </div>
            <div class=" pl-3 pb-4 drop-shadow-xl">
                email : {{ event.bookingEmail }}
            </div>
            <div class="pl-3 pb-4 drop-shadow-xl">
                Category : {{ event.categoryId }}
            </div>
            <div class="pl-3 pb-4 drop-shadow-xl">
                Duration : {{ event.eventDuration }} minutes
            </div>
            <div v-if="editMode == true">
                Date :<input type="datetime-local" v-model="newEvent.eventStartTime"
                    class="pl-2 pr-2 border border-gray-400 hover:border-gray-500 px-4 py-2 pr-7 rounded-2xl shadow leading-tight focus:outline-none focus:shadow-outline" />
            </div>
            <div v-else>
                <div class="pl-3 pb-4 drop-shadow-xl">
                    Date : {{ getDate(event.eventStartTime) }} Time :{{ getTime(event.eventStartTime) }}
                </div>
            </div>
            <div v-if="editMode == true">
                Note : <textarea v-model="newEvent.eventNotes"
                    class="w-full p-6  border border-gray-400 hover:border-gray-500 rounded-2xl shadow leading-tight focus:outline-none focus:shadow-outline resize-none"
                    rows="5" cols="100" />
            </div>
            <div v-else-if='event.eventNotes == "" || event.eventNotes == null' class=" pl-3 drop-shadow-xl">
                Note : -
            </div>
            <div v-else class="pl-3 pb-6 drop-shadow-xl  ">
                Note : {{ event.eventNotes }}
            </div>
            <div class="pt-4 flex gap-4 justify-center  ">
                <button v-if="editMode == true"
                    @click="$emit('getEditedEvent', newEvent), $emit('updateEvent'), toggleEditMode()"
                    class="bg-transparent hover:bg-green-500 text-green-700 font-semibold hover:text-white py-2 px-4 border border-green-500 hover:border-transparent rounded ">
                    summit
                </button>
                <button v-else @click="toggleEditMode()"
                    class="bg-transparent hover:bg-green-500 text-green-700 font-semibold hover:text-white py-2 px-4 border border-green-500 hover:border-transparent rounded ">
                    edit
                </button>
                <button v-if="editMode == true" @click="toggleEditMode()"
                    class="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded">
                    cancel
                </button>
                <button v-else @click="$emit('deleteEvent', event.id)"
                    class="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded">
                    delete
                </button>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
.gradient-color {
    background-image: linear-gradient(to top, #FFFFFF, #FBBF98);
}
</style>
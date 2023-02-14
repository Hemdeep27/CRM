<template>
  <div>
    <p>Hello</p>
    <select v-model="id" name="slist" id="slist" @change="dropDownHandler">
      <option
        v-for="(support, index) in list"
        :key="index"
        :value="list[index].userId"
      >
        {{ support.userName }}
      </option>
    </select>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      list: undefined,
      id: "",
    };
  },

  async created() {
    await axios.get("/api/admin/supportList").then((res) => {
      this.list = res.data;
    });
    console.log(this.list);
  },

  methods: {
    dropDownHandler(data) {
      data = this.id;
      console.log(data);
    },
  },
};
</script>

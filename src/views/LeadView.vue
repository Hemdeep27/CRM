<template>
  <div class="main">
    <div class="left">
      <div class="inner-left" @click="UnAssignedHandler">
        <h5>UnAssigned Leads</h5>
      </div>
      <div class="inner-left" @click="AssignedHandler">
        <h5>Assigned Leads</h5>
      </div>
    </div>
    <div class="right">
      <h2
        v-if="unassigned"
        style="padding-top: 9px,font-family: Arial, Helvetica, sans-serif"
      >
        UnAssigned Leads
      </h2>
      <h2
        v-if="assigned"
        style="padding-top: 9px,font-family: Arial, Helvetica, sans-serif"
      >
        Assigned Leads
      </h2>
      <div v-if="unassigned" class="inner-unassigned">
        <!-- {{ getUnAssignedLeads }} -->
        <div
          v-for="(unassigned, index) in getUnAssignedLeads"
          :key="unassigned.id"
          class="unassignedLead"
        >
          <p><b> Lead Id: </b>{{ unassigned.leadId }}</p>
          <p><b> Action: </b>{{ unassigned.action }}</p>
          <p v-if="unassigned.action === 'like'">
            <b> Ad: </b>{{ unassigned.ad }}
          </p>
          <p><b> Lead Info: </b>{{ unassigned.leadInfo }}</p>
          <select
            v-model="supportEngineerId[index].value"
            @change="DropDownHandler"
            required
            id="dropdown"
          >
            <!-- <option>--SELECT--</option> -->
            <option
              v-for="(support, index) in getSupportList"
              :key="index"
              :value="getSupportList[index].supportId"
            >
              {{ support.supportName }} &nbsp;
              <span v-if="support.isActive">&#9679;</span>
            </option>
          </select>
          <br />
          <button
            @click="
              AssignLead(supportEngineerId[index].value, unassigned.leadId)
            "
            class="button-17"
          >
            Assign
          </button>
          <br /><br />
        </div>
      </div>
      <div v-if="assigned" class="inner-assigned">
        <!-- <h1>Assigned Leads</h1> -->
        <!-- {{ getAssignedLeadList }} -->
        <div
          v-for="assigned in getAssignedLeadList"
          :key="assigned.id"
          class="assignedLead"
          style="border-radius: 9px"
        >
          <p><b> LeadId: </b>{{ assigned.leadId }}</p>
          <p><b> Lead Action: </b>{{ assigned.action }}</p>
          <p v-if="assigned.action === 'like'">
            <b> Lead Ad: </b>{{ assigned.ad }}
          </p>
          <p><b> Lead Assigned To: </b>{{ assigned.assignedName }}</p>
          <div style="over-flow: scroll">
            <b>Lead Info: </b>{{ assigned.leadInfo }}
          </div>
          <br />
          <p>
            <b> Lead Assigned On: </b>{{ TimeChange(assigned.createdTime) }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      unassigned: true,
      assigned: false,
      supportEngineerId: [],
    };
  },
  computed: {
    ...mapGetters([
      "getUnAssignedLeads",
      "getSupportList",
      "getAssignedLeadList",
    ]),
  },
  created() {
    this.$store.dispatch("ALL_LEADS", (leads) => {
      for (let i = 0; i < leads.length; i++) {
        this.supportEngineerId.push({ value: "" });
      }
    });
    this.$store.dispatch("SUPPORT_LIST");
  },
  methods: {
    ...mapActions(["ALL_LEADS", "SUPPORT_LIST", "ASSIGNED_LEADS_LIST"]),
    TimeChange(time) {
      console.log(new Date(time).toLocaleString());
      return new Date(time).toLocaleString();
    },
    DropDownHandler(value) {
      value = this.supportEngineerId;
      console.log("support Engineer List", value);
    },
    AssignLead(sid, lid) {
      console.log("supportId", sid);
      console.log("lead Id", lid);
      this.$store.dispatch("ASSIGN_LEAD", {
        lId: lid,
        sId: sid,
      });
      location.reload();
    },
    UnAssignedHandler() {
      this.unassigned = true;
      this.assigned = false;
    },
    AssignedHandler() {
      this.unassigned = false;
      this.assigned = true;
      this.$store.dispatch("ASSIGNED_LEADS_LIST");
    },
  },
};
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: row;
  background-color: white;
}
.assignedLead {
  /* border: 2px solid black;
  margin: 20px; */
  width: 500px;
  /* overflow: scroll; */
  word-wrap: break-word;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.239);
  /* border: 1px solid black; */
  /* border-radius: 8px; */
  margin: 20px;
  /* height: 50vh; */
  transition: 0.5s;
  text-align: left;
  padding: 15px;
}
.inner-assigned {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  flex-wrap: wrap;
}
.left {
  position: fixed;
  width: 25%;
  /* background-color: beige; */
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* border: 1px solid black; */
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
}
.inner-unassigned {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  flex-wrap: wrap;
}
.unassignedLead {
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  /* border: 1px solid black; */
  border-radius: 8px;
  margin: 20px;
  /* height: 50vh; */
  transition: 0.5s;
  width: 500px;
  padding: 15px;
  text-align: left;
}
.right {
  width: 70%;
  /* background-color: aqua; */
  height: 100%;
  margin-left: 25%;
}
#dropdown {
  width: 300px;
}
.inner-left {
  /* border: 0.5px solid black; */
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  margin: 20px;
  padding-top: 10px;
  padding-bottom: 10px;
  font-family: Georgia, "Times New Roman", Times, serif;
  cursor: pointer;
  border-radius: 6px;
  /* font-size: small; */
}

.button-17 {
  align-items: center;
  appearance: none;
  background-color: #fff;
  border-radius: 24px;
  border-style: none;
  box-shadow: rgba(0, 0, 0, 0.2) 0 3px 5px -1px,
    rgba(0, 0, 0, 0.14) 0 6px 10px 0, rgba(0, 0, 0, 0.12) 0 1px 18px 0;
  box-sizing: border-box;
  color: #3c4043;
  cursor: pointer;
  display: inline-flex;
  fill: currentcolor;
  font-family: "Google Sans", Roboto, Arial, sans-serif;
  font-size: 14px;
  font-weight: 500;
  height: 48px;
  justify-content: center;
  letter-spacing: 0.25px;
  line-height: normal;
  max-width: 100%;
  overflow: visible;
  padding: 2px 24px;
  position: relative;
  text-align: center;
  text-transform: none;
  transition: box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1),
    opacity 15ms linear 30ms, transform 270ms cubic-bezier(0, 0, 0.2, 1) 0ms;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: auto;
  will-change: transform, opacity;
  z-index: 0;
  float: right;
}

.button-17:hover {
  background: #f6f9fe;
  color: #174ea6;
}

.button-17:active {
  box-shadow: 0 4px 4px 0 rgb(60 64 67 / 30%),
    0 8px 12px 6px rgb(60 64 67 / 15%);
  outline: none;
}

.button-17:focus {
  outline: none;
  border: 2px solid #4285f4;
}

.button-17:not(:disabled) {
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 3px 0,
    rgba(60, 64, 67, 0.15) 0 4px 8px 3px;
}

.button-17:not(:disabled):hover {
  box-shadow: rgba(60, 64, 67, 0.3) 0 2px 3px 0,
    rgba(60, 64, 67, 0.15) 0 6px 10px 4px;
}

.button-17:not(:disabled):focus {
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 3px 0,
    rgba(60, 64, 67, 0.15) 0 4px 8px 3px;
}

.button-17:not(:disabled):active {
  box-shadow: rgba(60, 64, 67, 0.3) 0 4px 4px 0,
    rgba(60, 64, 67, 0.15) 0 8px 12px 6px;
}

.button-17:disabled {
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 3px 0,
    rgba(60, 64, 67, 0.15) 0 4px 8px 3px;
}
</style>

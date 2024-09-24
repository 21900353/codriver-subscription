<template>

    <v-card outlined>
        <v-card-title>
            Mypage # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Number label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="SubId" v-model="item.subId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Boolean label="SubStatus" v-model="item.subStatus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="Date" v-model="item.date" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Boolean label="SubProcessed" v-model="item.subProcessed" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MypageViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/mypages/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>


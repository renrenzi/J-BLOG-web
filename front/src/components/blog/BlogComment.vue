<template>
  <div>
    <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
      <el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>
      <div class="reply-info">
        <div
            tabindex="0"
            contenteditable="true"
            id="replyInput"
            spellcheck="false"
            placeholder="输入评论..."
            class="reply-input"
            @focus="showReplyBtn"
            @input="onDivInput($event)"
        ></div>
      </div>
      <div class="reply-btn-box" v-show="btnShow">
        <el-button
            class="reply-btn"
            size="medium"
            @click="sendComment"
            type="primary"
        >
          发表评论
        </el-button>
      </div>
    </div>
    <div
        v-for="(item, i) in comments"
        :key="i"
        class="author-title reply-father"
    >
      <el-avatar class="header-img" :size="40" :src="item.headImg"></el-avatar>
      <div class="author-info">
        <span class="author-name">{{ item.name }}</span>
        <span class="author-time">{{ item.time }}</span>
      </div>
      <div class="icon-btn">
        <span @click="showReplyInput(i, '0',item.name, item.id)">
          <i class="iconfont el-icon-s-comment"></i>
          {{ item.commentNum }}
        </span>
        <!-- <span  @click="addlikeNumber(i, item.from, item.id)">
        <i class="iconfont el-icon-caret-top"></i>
        {{ item.like }}
        </span> -->
        <span class="xin" @click="countlikeNumber('1',i,item.id)">
          <i class="iconfont el-icon-caret-top " v-if="item.isLike">&#xe607;</i>
          <i class="iconfont el-icon-caret-top" v-else>&#xe68b;</i>
          {{ item.like }}
        </span>
      </div>
      <div class="talk-box">
        <p>
          <span class="reply">{{ item.comment }}</span>
        </p>
      </div>
      <div class="reply-box">
        <div v-for="(reply, j) in item.reply" :key="j" class="author-title">
          <el-avatar
              class="header-img"
              :size="40"
              :src="reply.fromHeadImg"
          ></el-avatar>
          <div class="author-info">
            <span class="author-name">{{ reply.from }}</span>
            <span class="author-time">{{ reply.time }}</span>
          </div>
          <div class="icon-btn">
            <span @click="showReplyInput(i,j, reply.from, reply.id)">
              <i class="iconfont el-icon-s-comment"></i>
              {{ reply.commentNum }}
            </span>
            <span  @click="countlikeNumber('2',i,j,reply)">
            <i class="iconfont el-icon-caret-top"></i>
            {{ reply.like }}
            </span>
          </div>
          <div class="talk-box">
            <p>
              <span>回复 {{ reply.to }}:</span>
              <span class="reply">{{ reply.comment }}</span>
            </p>
          </div>
          <div class="reply-box"></div>
        </div>
      </div>
      <div v-show="_inputShow(i)" class="my-reply my-comment-reply">
        <el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>
        <div class="reply-info">
          <div
              tabindex="0"
              contenteditable="true"
              spellcheck="false"
              placeholder="输入评论..."
              @input="onDivInput($event)"
              class="reply-input reply-comment-input"
          ></div>
        </div>
        <div class="reply-btn-box">
          <el-button
              class="reply-btn"
              size="medium"
              @click="sendCommentReply(i)"
              type="primary"
          >
            发表回复
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import clickoutside from "element-ui/src/utils/clickoutside";

export default {
  name: "BlogComment",
  data() {
    return {
      btnShow: false,
      index: '0',
      replyComment: '',
      subIndex:'0',
      myName: 'Lana Del Rey',
      myHeader:
          'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
      myId: 19870621,
      to: '',
      toId: -1,
      comments: [
        {
          name: 'Lana Del Rey',
          id: 19870621,
          headImg:
              'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
          comment: '我发布一张新专辑Norman Fucking Rockwell,大家快来听啊',
          time: '2019年9月16日 18:43',
          commentNum: 2,
          like: 15,
          isLike:false,
          likeListId:[],//存放已点赞用户的id
          inputShow: false,
          reply: [
            {
              from: 'Taylor Swift',
              fromId: 19891221,
              fromHeadImg:
                  'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
              to: 'Lana Del Rey',
              toId: 19870621,
              comment: '我很喜欢你的新专辑！！',
              time: '2019年9月16日 18:43',
              commentNum: 1,
              like: 15,
              isLike:false,
              likeListId:[],
              inputShow: false,
            },
            {
              from: 'Ariana Grande',
              fromId: 1123,
              fromHeadImg:
                  'https://ae01.alicdn.com/kf/Hf6c0b4a7428b4edf866a9fbab75568e6U.jpg',
              to: 'Lana Del Rey',
              toId: 19870621,
              comment: '别忘记宣传我们的合作单曲啊',
              time: '2019年9月16日 18:43',
              commentNum: 0,
              like: 5,
              isLike:false,
              likeListId:[],
              inputShow: false,
            },
          ],
        },
        {
          name: 'Taylor Swift',
          id: 19891221,
          headImg:
              'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
          comment: '我发行了我的新专辑Lover',
          time: '2019年9月16日 18:43',
          commentNum: 1,
          like: 5,
          likeListId:[],
          inputShow: false,
          reply: [
            {
              from: 'Lana Del Rey',
              fromId: 19870621,
              fromHeadImg:
                  'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
              to: 'Taylor Swift',
              toId: 19891221,
              comment: '新专辑和speak now 一样棒！',
              time: '2019年9月16日 18:43',
              commentNum: 25,
              like: 5,
              isLike:false,
              likeListId:[],
              inputShow: false,
            },
          ],
        },
        {
          name: 'Norman Fucking Rockwell',
          id: 20190830,
          headImg:
              'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
          comment: 'Plz buy Norman Fucking Rockwell on everywhere',
          time: '2019年9月16日 18:43',
          commentNum: 0,
          like: 5,
          isLike:false,
          likeListId:[],
          inputShow: false,
          reply: [],
        },
      ],
    }
  },
  directives: {  },
  methods: {
    inputFocus() {
      var replyInput = document.getElementById('replyInput')
      replyInput.style.padding = '8px 8px'
      replyInput.style.border = '2px solid blue'
      replyInput.focus()
    },
    showReplyBtn() {
      this.btnShow = true
    },
    hideReplyBtn() {
      this.btnShow = false
      replyInput.style.padding = '10px'
      replyInput.style.border = 'none'
    },
    showReplyInput(i,j,name,id) {
      this.comments[this.index].inputShow = false
      this.index = i
      this.comments[i].inputShow = true
      this.to = name
      this.toId = id
      this.subIndex = j == '0' ? '0':j
    },
    addlikeNumber(i,id) {
      let list = this.comments[i].likeListId
      if(list.length === 0){
        //在已经点赞的列表中未找到userId
        this.comments[i].isLike = true
        this.comments[i].like += 1
        this.comments[i].likeListId.push(id)
        console.log("点赞+1",this.comments[i].isLike,this.comments[i].like,this.comments[i].likeListId)
      }
      else{
        const index = list.indexOf(this.myId)
        this.comments[i].isLike = false
        this.comments[i].like -= 1
        this.comments[i].likeListId.splice(index,1)
        console.log("点赞-1",this.comments[i].isLike,this.comments[i].likeListId)
      }

    },
    countlikeNumber(type,i,j,id) {
      const text =  type === '1' ? this.comments[i] : this.comments[i].reply[j]
      let list = text.likeListId
      if(list.length === 0){
        //在已经点赞的列表中未找到userId
        text.isLike = true
        text.like += 1
        text.likeListId.push(id)
        console.log("点赞+1",text.isLike,text.like,text.likeListId)
      }
      else{
        const index = list.indexOf(this.myId)
        text.isLike = false
        text.like -= 1
        text.likeListId.splice(index,1)
        console.log("点赞-1",text.isLike,text.likeListId)
      }

    },
    _inputShow(i) {
      return this.comments[i].inputShow
    },
    sendComment() {
      if (!this.replyComment) {
        this.$message({
          showClose: true,
          type: 'warning',
          message: '评论不能为空',
        })
      } else {
        let a = {}
        let input = document.getElementById('replyInput')
        let timeNow = new Date().getTime()
        let time = this.dateStr(timeNow)
        a.name = this.myName
        a.comment = this.replyComment
        a.headImg = this.myHeader
        a.time = time
        a.commentNum = 0
        a.like = 0
        a.id = this.myId
        a.reply = [],
            a.isLike = false,
            a.likeListId = [],
            this.comments.push(a)
        this.replyComment = ''
        input.innerHTML = ''
      }
    },
    sendCommentReply(i) {
      if (!this.replyComment) {
        this.$message({
          showClose: true,
          type: 'warning',
          message: '回复不能为空',
        })
      } else {
        let a = {}
        let timeNow = new Date().getTime()
        let time = this.dateStr(timeNow)
        a.from = this.myName
        a.to = this.to
        a.fromHeadImg = this.myHeader
        a.comment = this.replyComment
        a.time = time
        a.commentNum = 0
        a.like = 0
        a.isLike = false
        a.likeListId = []
        console.log(" this.comments[i].reply+++++++++++", this.comments[i].reply,this.subIndex)
        if(this.subIndex === '0'){
          this.comments[i].commentNum += 1
        }else{
          this.comments[i].reply[this.subIndex].commentNum += 1
        }
        this.comments[i].reply.push(a)
        this.replyComment = ''
        document.getElementsByClassName('reply-comment-input')[i].innerHTML =
            ''
      }
    },
    onDivInput: function (e) {
      this.replyComment = e.target.innerHTML
    },
    dateStr(date) {
      //获取js 时间戳
      var time = new Date().getTime()
      //去掉 js 时间戳后三位，与php 时间戳保持一致
      time = parseInt((time - date) / 1000)
      //存储转换值
      var s
      if (time < 60 * 10) {
        //十分钟内
        return '刚刚'
      } else if (time < 60 * 60 && time >= 60 * 10) {
        //超过十分钟少于1小时
        s = Math.floor(time / 60)
        return s + '分钟前'
      } else if (time < 60 * 60 * 24 && time >= 60 * 60) {
        //超过1小时少于24小时
        s = Math.floor(time / 60 / 60)
        return s + '小时前'
      } else if (time < 60 * 60 * 24 * 30 && time >= 60 * 60 * 24) {
        //超过1天少于30天内
        s = Math.floor(time / 60 / 60 / 24)
        return s + '天前'
      } else {
        //超过30天ddd
        var date = new Date(parseInt(date))
        return (
            date.getFullYear() +
            '/' +
            (date.getMonth() + 1) +
            '/' +
            date.getDate()
        )
      }
    },
  },

}
</script>

<style  scoped>


</style>

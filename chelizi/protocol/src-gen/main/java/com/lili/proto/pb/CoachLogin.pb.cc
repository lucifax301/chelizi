// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/java/com/lili/proto/pb/CoachLogin.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "main/java/com/lili/proto/pb/CoachLogin.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace com {
namespace lili {
namespace proto {
namespace pb {

namespace {

const ::google::protobuf::Descriptor* CoachLoginMsg_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  CoachLoginMsg_reflection_ = NULL;
const ::google::protobuf::Descriptor* CoachLoginAckMsg_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  CoachLoginAckMsg_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto() {
  protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "main/java/com/lili/proto/pb/CoachLogin.proto");
  GOOGLE_CHECK(file != NULL);
  CoachLoginMsg_descriptor_ = file->message_type(0);
  static const int CoachLoginMsg_offsets_[3] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginMsg, token_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginMsg, phonenum_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginMsg, coachid_),
  };
  CoachLoginMsg_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      CoachLoginMsg_descriptor_,
      CoachLoginMsg::default_instance_,
      CoachLoginMsg_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginMsg, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginMsg, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(CoachLoginMsg));
  CoachLoginAckMsg_descriptor_ = file->message_type(1);
  static const int CoachLoginAckMsg_offsets_[1] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginAckMsg, issuccess_),
  };
  CoachLoginAckMsg_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      CoachLoginAckMsg_descriptor_,
      CoachLoginAckMsg::default_instance_,
      CoachLoginAckMsg_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginAckMsg, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachLoginAckMsg, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(CoachLoginAckMsg));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    CoachLoginMsg_descriptor_, &CoachLoginMsg::default_instance());
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    CoachLoginAckMsg_descriptor_, &CoachLoginAckMsg::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto() {
  delete CoachLoginMsg::default_instance_;
  delete CoachLoginMsg_reflection_;
  delete CoachLoginAckMsg::default_instance_;
  delete CoachLoginAckMsg_reflection_;
}

void protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n,main/java/com/lili/proto/pb/CoachLogin"
    ".proto\022\021com.lili.proto.pb\"A\n\rCoachLoginM"
    "sg\022\r\n\005token\030\001 \002(\t\022\020\n\010phoneNum\030\002 \002(\t\022\017\n\007c"
    "oachId\030\003 \002(\003\"%\n\020CoachLoginAckMsg\022\021\n\tisSu"
    "ccess\030\001 \002(\010B&\n\023com.lili.proto.javaB\017Coac"
    "hLoginProto", 211);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "main/java/com/lili/proto/pb/CoachLogin.proto", &protobuf_RegisterTypes);
  CoachLoginMsg::default_instance_ = new CoachLoginMsg();
  CoachLoginAckMsg::default_instance_ = new CoachLoginAckMsg();
  CoachLoginMsg::default_instance_->InitAsDefaultInstance();
  CoachLoginAckMsg::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto {
  StaticDescriptorInitializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto() {
    protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto();
  }
} static_descriptor_initializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto_;

// ===================================================================

#ifndef _MSC_VER
const int CoachLoginMsg::kTokenFieldNumber;
const int CoachLoginMsg::kPhoneNumFieldNumber;
const int CoachLoginMsg::kCoachIdFieldNumber;
#endif  // !_MSC_VER

CoachLoginMsg::CoachLoginMsg()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void CoachLoginMsg::InitAsDefaultInstance() {
}

CoachLoginMsg::CoachLoginMsg(const CoachLoginMsg& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void CoachLoginMsg::SharedCtor() {
  _cached_size_ = 0;
  token_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  phonenum_ = const_cast< ::std::string*>(&::google::protobuf::internal::kEmptyString);
  coachid_ = GOOGLE_LONGLONG(0);
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

CoachLoginMsg::~CoachLoginMsg() {
  SharedDtor();
}

void CoachLoginMsg::SharedDtor() {
  if (token_ != &::google::protobuf::internal::kEmptyString) {
    delete token_;
  }
  if (phonenum_ != &::google::protobuf::internal::kEmptyString) {
    delete phonenum_;
  }
  if (this != default_instance_) {
  }
}

void CoachLoginMsg::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* CoachLoginMsg::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return CoachLoginMsg_descriptor_;
}

const CoachLoginMsg& CoachLoginMsg::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto();
  return *default_instance_;
}

CoachLoginMsg* CoachLoginMsg::default_instance_ = NULL;

CoachLoginMsg* CoachLoginMsg::New() const {
  return new CoachLoginMsg;
}

void CoachLoginMsg::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (has_token()) {
      if (token_ != &::google::protobuf::internal::kEmptyString) {
        token_->clear();
      }
    }
    if (has_phonenum()) {
      if (phonenum_ != &::google::protobuf::internal::kEmptyString) {
        phonenum_->clear();
      }
    }
    coachid_ = GOOGLE_LONGLONG(0);
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool CoachLoginMsg::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required string token = 1;
      case 1: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_LENGTH_DELIMITED) {
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_token()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8String(
            this->token().data(), this->token().length(),
            ::google::protobuf::internal::WireFormat::PARSE);
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(18)) goto parse_phoneNum;
        break;
      }

      // required string phoneNum = 2;
      case 2: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_LENGTH_DELIMITED) {
         parse_phoneNum:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_phonenum()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8String(
            this->phonenum().data(), this->phonenum().length(),
            ::google::protobuf::internal::WireFormat::PARSE);
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(24)) goto parse_coachId;
        break;
      }

      // required int64 coachId = 3;
      case 3: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
         parse_coachId:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &coachid_)));
          set_has_coachid();
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectAtEnd()) return true;
        break;
      }

      default: {
      handle_uninterpreted:
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          return true;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
  return true;
#undef DO_
}

void CoachLoginMsg::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required string token = 1;
  if (has_token()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8String(
      this->token().data(), this->token().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE);
    ::google::protobuf::internal::WireFormatLite::WriteString(
      1, this->token(), output);
  }

  // required string phoneNum = 2;
  if (has_phonenum()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8String(
      this->phonenum().data(), this->phonenum().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE);
    ::google::protobuf::internal::WireFormatLite::WriteString(
      2, this->phonenum(), output);
  }

  // required int64 coachId = 3;
  if (has_coachid()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(3, this->coachid(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* CoachLoginMsg::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required string token = 1;
  if (has_token()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8String(
      this->token().data(), this->token().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE);
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->token(), target);
  }

  // required string phoneNum = 2;
  if (has_phonenum()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8String(
      this->phonenum().data(), this->phonenum().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE);
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        2, this->phonenum(), target);
  }

  // required int64 coachId = 3;
  if (has_coachid()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(3, this->coachid(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int CoachLoginMsg::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required string token = 1;
    if (has_token()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::StringSize(
          this->token());
    }

    // required string phoneNum = 2;
    if (has_phonenum()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::StringSize(
          this->phonenum());
    }

    // required int64 coachId = 3;
    if (has_coachid()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->coachid());
    }

  }
  if (!unknown_fields().empty()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void CoachLoginMsg::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const CoachLoginMsg* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const CoachLoginMsg*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void CoachLoginMsg::MergeFrom(const CoachLoginMsg& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_token()) {
      set_token(from.token());
    }
    if (from.has_phonenum()) {
      set_phonenum(from.phonenum());
    }
    if (from.has_coachid()) {
      set_coachid(from.coachid());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void CoachLoginMsg::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void CoachLoginMsg::CopyFrom(const CoachLoginMsg& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool CoachLoginMsg::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000007) != 0x00000007) return false;

  return true;
}

void CoachLoginMsg::Swap(CoachLoginMsg* other) {
  if (other != this) {
    std::swap(token_, other->token_);
    std::swap(phonenum_, other->phonenum_);
    std::swap(coachid_, other->coachid_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata CoachLoginMsg::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = CoachLoginMsg_descriptor_;
  metadata.reflection = CoachLoginMsg_reflection_;
  return metadata;
}


// ===================================================================

#ifndef _MSC_VER
const int CoachLoginAckMsg::kIsSuccessFieldNumber;
#endif  // !_MSC_VER

CoachLoginAckMsg::CoachLoginAckMsg()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void CoachLoginAckMsg::InitAsDefaultInstance() {
}

CoachLoginAckMsg::CoachLoginAckMsg(const CoachLoginAckMsg& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void CoachLoginAckMsg::SharedCtor() {
  _cached_size_ = 0;
  issuccess_ = false;
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

CoachLoginAckMsg::~CoachLoginAckMsg() {
  SharedDtor();
}

void CoachLoginAckMsg::SharedDtor() {
  if (this != default_instance_) {
  }
}

void CoachLoginAckMsg::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* CoachLoginAckMsg::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return CoachLoginAckMsg_descriptor_;
}

const CoachLoginAckMsg& CoachLoginAckMsg::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachLogin_2eproto();
  return *default_instance_;
}

CoachLoginAckMsg* CoachLoginAckMsg::default_instance_ = NULL;

CoachLoginAckMsg* CoachLoginAckMsg::New() const {
  return new CoachLoginAckMsg;
}

void CoachLoginAckMsg::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    issuccess_ = false;
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool CoachLoginAckMsg::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required bool isSuccess = 1;
      case 1: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &issuccess_)));
          set_has_issuccess();
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectAtEnd()) return true;
        break;
      }

      default: {
      handle_uninterpreted:
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          return true;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
  return true;
#undef DO_
}

void CoachLoginAckMsg::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required bool isSuccess = 1;
  if (has_issuccess()) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(1, this->issuccess(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* CoachLoginAckMsg::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required bool isSuccess = 1;
  if (has_issuccess()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(1, this->issuccess(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int CoachLoginAckMsg::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required bool isSuccess = 1;
    if (has_issuccess()) {
      total_size += 1 + 1;
    }

  }
  if (!unknown_fields().empty()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void CoachLoginAckMsg::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const CoachLoginAckMsg* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const CoachLoginAckMsg*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void CoachLoginAckMsg::MergeFrom(const CoachLoginAckMsg& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_issuccess()) {
      set_issuccess(from.issuccess());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void CoachLoginAckMsg::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void CoachLoginAckMsg::CopyFrom(const CoachLoginAckMsg& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool CoachLoginAckMsg::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;

  return true;
}

void CoachLoginAckMsg::Swap(CoachLoginAckMsg* other) {
  if (other != this) {
    std::swap(issuccess_, other->issuccess_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata CoachLoginAckMsg::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = CoachLoginAckMsg_descriptor_;
  metadata.reflection = CoachLoginAckMsg_reflection_;
  return metadata;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace pb
}  // namespace proto
}  // namespace lili
}  // namespace com

// @@protoc_insertion_point(global_scope)

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/java/com/lili/proto/pb/Ping.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "main/java/com/lili/proto/pb/Ping.pb.h"

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

const ::google::protobuf::Descriptor* PingMsg_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  PingMsg_reflection_ = NULL;
const ::google::protobuf::Descriptor* PongMsg_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  PongMsg_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto() {
  protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "main/java/com/lili/proto/pb/Ping.proto");
  GOOGLE_CHECK(file != NULL);
  PingMsg_descriptor_ = file->message_type(0);
  static const int PingMsg_offsets_[1] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(PingMsg, clienttimestamp_),
  };
  PingMsg_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      PingMsg_descriptor_,
      PingMsg::default_instance_,
      PingMsg_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(PingMsg, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(PingMsg, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(PingMsg));
  PongMsg_descriptor_ = file->message_type(1);
  static const int PongMsg_offsets_[1] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(PongMsg, clienttimestamp_),
  };
  PongMsg_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      PongMsg_descriptor_,
      PongMsg::default_instance_,
      PongMsg_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(PongMsg, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(PongMsg, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(PongMsg));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    PingMsg_descriptor_, &PingMsg::default_instance());
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    PongMsg_descriptor_, &PongMsg::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto() {
  delete PingMsg::default_instance_;
  delete PingMsg_reflection_;
  delete PongMsg::default_instance_;
  delete PongMsg_reflection_;
}

void protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n&main/java/com/lili/proto/pb/Ping.proto"
    "\022\021com.lili.proto.pb\"\"\n\007PingMsg\022\027\n\017client"
    "Timestamp\030\001 \002(\003\"\"\n\007PongMsg\022\027\n\017clientTime"
    "stamp\030\001 \002(\003B \n\023com.lili.proto.javaB\tPing"
    "Proto", 165);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "main/java/com/lili/proto/pb/Ping.proto", &protobuf_RegisterTypes);
  PingMsg::default_instance_ = new PingMsg();
  PongMsg::default_instance_ = new PongMsg();
  PingMsg::default_instance_->InitAsDefaultInstance();
  PongMsg::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto {
  StaticDescriptorInitializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto() {
    protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto();
  }
} static_descriptor_initializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto_;

// ===================================================================

#ifndef _MSC_VER
const int PingMsg::kClientTimestampFieldNumber;
#endif  // !_MSC_VER

PingMsg::PingMsg()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void PingMsg::InitAsDefaultInstance() {
}

PingMsg::PingMsg(const PingMsg& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void PingMsg::SharedCtor() {
  _cached_size_ = 0;
  clienttimestamp_ = GOOGLE_LONGLONG(0);
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

PingMsg::~PingMsg() {
  SharedDtor();
}

void PingMsg::SharedDtor() {
  if (this != default_instance_) {
  }
}

void PingMsg::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* PingMsg::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return PingMsg_descriptor_;
}

const PingMsg& PingMsg::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto();
  return *default_instance_;
}

PingMsg* PingMsg::default_instance_ = NULL;

PingMsg* PingMsg::New() const {
  return new PingMsg;
}

void PingMsg::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    clienttimestamp_ = GOOGLE_LONGLONG(0);
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool PingMsg::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required int64 clientTimestamp = 1;
      case 1: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &clienttimestamp_)));
          set_has_clienttimestamp();
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

void PingMsg::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required int64 clientTimestamp = 1;
  if (has_clienttimestamp()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(1, this->clienttimestamp(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* PingMsg::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required int64 clientTimestamp = 1;
  if (has_clienttimestamp()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(1, this->clienttimestamp(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int PingMsg::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required int64 clientTimestamp = 1;
    if (has_clienttimestamp()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->clienttimestamp());
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

void PingMsg::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const PingMsg* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const PingMsg*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void PingMsg::MergeFrom(const PingMsg& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_clienttimestamp()) {
      set_clienttimestamp(from.clienttimestamp());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void PingMsg::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void PingMsg::CopyFrom(const PingMsg& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool PingMsg::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;

  return true;
}

void PingMsg::Swap(PingMsg* other) {
  if (other != this) {
    std::swap(clienttimestamp_, other->clienttimestamp_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata PingMsg::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = PingMsg_descriptor_;
  metadata.reflection = PingMsg_reflection_;
  return metadata;
}


// ===================================================================

#ifndef _MSC_VER
const int PongMsg::kClientTimestampFieldNumber;
#endif  // !_MSC_VER

PongMsg::PongMsg()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void PongMsg::InitAsDefaultInstance() {
}

PongMsg::PongMsg(const PongMsg& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void PongMsg::SharedCtor() {
  _cached_size_ = 0;
  clienttimestamp_ = GOOGLE_LONGLONG(0);
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

PongMsg::~PongMsg() {
  SharedDtor();
}

void PongMsg::SharedDtor() {
  if (this != default_instance_) {
  }
}

void PongMsg::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* PongMsg::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return PongMsg_descriptor_;
}

const PongMsg& PongMsg::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fPing_2eproto();
  return *default_instance_;
}

PongMsg* PongMsg::default_instance_ = NULL;

PongMsg* PongMsg::New() const {
  return new PongMsg;
}

void PongMsg::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    clienttimestamp_ = GOOGLE_LONGLONG(0);
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool PongMsg::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required int64 clientTimestamp = 1;
      case 1: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_VARINT) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &clienttimestamp_)));
          set_has_clienttimestamp();
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

void PongMsg::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required int64 clientTimestamp = 1;
  if (has_clienttimestamp()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(1, this->clienttimestamp(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* PongMsg::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required int64 clientTimestamp = 1;
  if (has_clienttimestamp()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(1, this->clienttimestamp(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int PongMsg::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required int64 clientTimestamp = 1;
    if (has_clienttimestamp()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->clienttimestamp());
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

void PongMsg::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const PongMsg* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const PongMsg*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void PongMsg::MergeFrom(const PongMsg& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_clienttimestamp()) {
      set_clienttimestamp(from.clienttimestamp());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void PongMsg::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void PongMsg::CopyFrom(const PongMsg& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool PongMsg::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;

  return true;
}

void PongMsg::Swap(PongMsg* other) {
  if (other != this) {
    std::swap(clienttimestamp_, other->clienttimestamp_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata PongMsg::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = PongMsg_descriptor_;
  metadata.reflection = PongMsg_reflection_;
  return metadata;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace pb
}  // namespace proto
}  // namespace lili
}  // namespace com

// @@protoc_insertion_point(global_scope)

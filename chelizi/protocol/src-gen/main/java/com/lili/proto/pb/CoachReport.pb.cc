// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/java/com/lili/proto/pb/CoachReport.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "main/java/com/lili/proto/pb/CoachReport.pb.h"

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

const ::google::protobuf::Descriptor* CoachReportMsg_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  CoachReportMsg_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto() {
  protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "main/java/com/lili/proto/pb/CoachReport.proto");
  GOOGLE_CHECK(file != NULL);
  CoachReportMsg_descriptor_ = file->message_type(0);
  static const int CoachReportMsg_offsets_[3] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachReportMsg, lge_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachReportMsg, lae_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachReportMsg, dir_),
  };
  CoachReportMsg_reflection_ =
    new ::google::protobuf::internal::GeneratedMessageReflection(
      CoachReportMsg_descriptor_,
      CoachReportMsg::default_instance_,
      CoachReportMsg_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachReportMsg, _has_bits_[0]),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(CoachReportMsg, _unknown_fields_),
      -1,
      ::google::protobuf::DescriptorPool::generated_pool(),
      ::google::protobuf::MessageFactory::generated_factory(),
      sizeof(CoachReportMsg));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
    CoachReportMsg_descriptor_, &CoachReportMsg::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto() {
  delete CoachReportMsg::default_instance_;
  delete CoachReportMsg_reflection_;
}

void protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n-main/java/com/lili/proto/pb/CoachRepor"
    "t.proto\022\021com.lili.proto.pb\"7\n\016CoachRepor"
    "tMsg\022\013\n\003lge\030\001 \002(\001\022\013\n\003lae\030\002 \002(\001\022\013\n\003dir\030\003 "
    "\002(\002B\'\n\023com.lili.proto.javaB\020CoachReportP"
    "roto", 164);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "main/java/com/lili/proto/pb/CoachReport.proto", &protobuf_RegisterTypes);
  CoachReportMsg::default_instance_ = new CoachReportMsg();
  CoachReportMsg::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto {
  StaticDescriptorInitializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto() {
    protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto();
  }
} static_descriptor_initializer_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto_;

// ===================================================================

#ifndef _MSC_VER
const int CoachReportMsg::kLgeFieldNumber;
const int CoachReportMsg::kLaeFieldNumber;
const int CoachReportMsg::kDirFieldNumber;
#endif  // !_MSC_VER

CoachReportMsg::CoachReportMsg()
  : ::google::protobuf::Message() {
  SharedCtor();
}

void CoachReportMsg::InitAsDefaultInstance() {
}

CoachReportMsg::CoachReportMsg(const CoachReportMsg& from)
  : ::google::protobuf::Message() {
  SharedCtor();
  MergeFrom(from);
}

void CoachReportMsg::SharedCtor() {
  _cached_size_ = 0;
  lge_ = 0;
  lae_ = 0;
  dir_ = 0;
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

CoachReportMsg::~CoachReportMsg() {
  SharedDtor();
}

void CoachReportMsg::SharedDtor() {
  if (this != default_instance_) {
  }
}

void CoachReportMsg::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* CoachReportMsg::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return CoachReportMsg_descriptor_;
}

const CoachReportMsg& CoachReportMsg::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_main_2fjava_2fcom_2flili_2fproto_2fpb_2fCoachReport_2eproto();
  return *default_instance_;
}

CoachReportMsg* CoachReportMsg::default_instance_ = NULL;

CoachReportMsg* CoachReportMsg::New() const {
  return new CoachReportMsg;
}

void CoachReportMsg::Clear() {
  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    lge_ = 0;
    lae_ = 0;
    dir_ = 0;
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  mutable_unknown_fields()->Clear();
}

bool CoachReportMsg::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) return false
  ::google::protobuf::uint32 tag;
  while ((tag = input->ReadTag()) != 0) {
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required double lge = 1;
      case 1: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_FIXED64) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   double, ::google::protobuf::internal::WireFormatLite::TYPE_DOUBLE>(
                 input, &lge_)));
          set_has_lge();
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(17)) goto parse_lae;
        break;
      }

      // required double lae = 2;
      case 2: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_FIXED64) {
         parse_lae:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   double, ::google::protobuf::internal::WireFormatLite::TYPE_DOUBLE>(
                 input, &lae_)));
          set_has_lae();
        } else {
          goto handle_uninterpreted;
        }
        if (input->ExpectTag(29)) goto parse_dir;
        break;
      }

      // required float dir = 3;
      case 3: {
        if (::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_FIXED32) {
         parse_dir:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   float, ::google::protobuf::internal::WireFormatLite::TYPE_FLOAT>(
                 input, &dir_)));
          set_has_dir();
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

void CoachReportMsg::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // required double lge = 1;
  if (has_lge()) {
    ::google::protobuf::internal::WireFormatLite::WriteDouble(1, this->lge(), output);
  }

  // required double lae = 2;
  if (has_lae()) {
    ::google::protobuf::internal::WireFormatLite::WriteDouble(2, this->lae(), output);
  }

  // required float dir = 3;
  if (has_dir()) {
    ::google::protobuf::internal::WireFormatLite::WriteFloat(3, this->dir(), output);
  }

  if (!unknown_fields().empty()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
}

::google::protobuf::uint8* CoachReportMsg::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // required double lge = 1;
  if (has_lge()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteDoubleToArray(1, this->lge(), target);
  }

  // required double lae = 2;
  if (has_lae()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteDoubleToArray(2, this->lae(), target);
  }

  // required float dir = 3;
  if (has_dir()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteFloatToArray(3, this->dir(), target);
  }

  if (!unknown_fields().empty()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  return target;
}

int CoachReportMsg::ByteSize() const {
  int total_size = 0;

  if (_has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    // required double lge = 1;
    if (has_lge()) {
      total_size += 1 + 8;
    }

    // required double lae = 2;
    if (has_lae()) {
      total_size += 1 + 8;
    }

    // required float dir = 3;
    if (has_dir()) {
      total_size += 1 + 4;
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

void CoachReportMsg::MergeFrom(const ::google::protobuf::Message& from) {
  GOOGLE_CHECK_NE(&from, this);
  const CoachReportMsg* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const CoachReportMsg*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void CoachReportMsg::MergeFrom(const CoachReportMsg& from) {
  GOOGLE_CHECK_NE(&from, this);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_lge()) {
      set_lge(from.lge());
    }
    if (from.has_lae()) {
      set_lae(from.lae());
    }
    if (from.has_dir()) {
      set_dir(from.dir());
    }
  }
  mutable_unknown_fields()->MergeFrom(from.unknown_fields());
}

void CoachReportMsg::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void CoachReportMsg::CopyFrom(const CoachReportMsg& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool CoachReportMsg::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000007) != 0x00000007) return false;

  return true;
}

void CoachReportMsg::Swap(CoachReportMsg* other) {
  if (other != this) {
    std::swap(lge_, other->lge_);
    std::swap(lae_, other->lae_);
    std::swap(dir_, other->dir_);
    std::swap(_has_bits_[0], other->_has_bits_[0]);
    _unknown_fields_.Swap(&other->_unknown_fields_);
    std::swap(_cached_size_, other->_cached_size_);
  }
}

::google::protobuf::Metadata CoachReportMsg::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = CoachReportMsg_descriptor_;
  metadata.reflection = CoachReportMsg_reflection_;
  return metadata;
}


// @@protoc_insertion_point(namespace_scope)

}  // namespace pb
}  // namespace proto
}  // namespace lili
}  // namespace com

// @@protoc_insertion_point(global_scope)

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/java/com/lili/proto/pb/CoachReport.proto

package com.lili.proto.java;

public final class CoachReportProto {
  private CoachReportProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface CoachReportMsgOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required double lge = 1;
    /**
     * <code>required double lge = 1;</code>
     */
    boolean hasLge();
    /**
     * <code>required double lge = 1;</code>
     */
    double getLge();

    // required double lae = 2;
    /**
     * <code>required double lae = 2;</code>
     */
    boolean hasLae();
    /**
     * <code>required double lae = 2;</code>
     */
    double getLae();

    // required float dir = 3;
    /**
     * <code>required float dir = 3;</code>
     */
    boolean hasDir();
    /**
     * <code>required float dir = 3;</code>
     */
    float getDir();
  }
  /**
   * Protobuf type {@code com.lili.proto.pb.CoachReportMsg}
   */
  public static final class CoachReportMsg extends
      com.google.protobuf.GeneratedMessage
      implements CoachReportMsgOrBuilder {
    // Use CoachReportMsg.newBuilder() to construct.
    private CoachReportMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private CoachReportMsg(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final CoachReportMsg defaultInstance;
    public static CoachReportMsg getDefaultInstance() {
      return defaultInstance;
    }

    public CoachReportMsg getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private CoachReportMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 9: {
              bitField0_ |= 0x00000001;
              lge_ = input.readDouble();
              break;
            }
            case 17: {
              bitField0_ |= 0x00000002;
              lae_ = input.readDouble();
              break;
            }
            case 29: {
              bitField0_ |= 0x00000004;
              dir_ = input.readFloat();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.lili.proto.java.CoachReportProto.internal_static_com_lili_proto_pb_CoachReportMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.lili.proto.java.CoachReportProto.internal_static_com_lili_proto_pb_CoachReportMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.lili.proto.java.CoachReportProto.CoachReportMsg.class, com.lili.proto.java.CoachReportProto.CoachReportMsg.Builder.class);
    }

    public static com.google.protobuf.Parser<CoachReportMsg> PARSER =
        new com.google.protobuf.AbstractParser<CoachReportMsg>() {
      public CoachReportMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new CoachReportMsg(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<CoachReportMsg> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required double lge = 1;
    public static final int LGE_FIELD_NUMBER = 1;
    private double lge_;
    /**
     * <code>required double lge = 1;</code>
     */
    public boolean hasLge() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required double lge = 1;</code>
     */
    public double getLge() {
      return lge_;
    }

    // required double lae = 2;
    public static final int LAE_FIELD_NUMBER = 2;
    private double lae_;
    /**
     * <code>required double lae = 2;</code>
     */
    public boolean hasLae() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required double lae = 2;</code>
     */
    public double getLae() {
      return lae_;
    }

    // required float dir = 3;
    public static final int DIR_FIELD_NUMBER = 3;
    private float dir_;
    /**
     * <code>required float dir = 3;</code>
     */
    public boolean hasDir() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required float dir = 3;</code>
     */
    public float getDir() {
      return dir_;
    }

    private void initFields() {
      lge_ = 0D;
      lae_ = 0D;
      dir_ = 0F;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasLge()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasLae()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasDir()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeDouble(1, lge_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeDouble(2, lae_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeFloat(3, dir_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeDoubleSize(1, lge_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeDoubleSize(2, lae_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(3, dir_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.lili.proto.java.CoachReportProto.CoachReportMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.lili.proto.java.CoachReportProto.CoachReportMsg prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.lili.proto.pb.CoachReportMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.lili.proto.java.CoachReportProto.CoachReportMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.lili.proto.java.CoachReportProto.internal_static_com_lili_proto_pb_CoachReportMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.lili.proto.java.CoachReportProto.internal_static_com_lili_proto_pb_CoachReportMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.lili.proto.java.CoachReportProto.CoachReportMsg.class, com.lili.proto.java.CoachReportProto.CoachReportMsg.Builder.class);
      }

      // Construct using com.lili.proto.java.CoachReportProto.CoachReportMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        lge_ = 0D;
        bitField0_ = (bitField0_ & ~0x00000001);
        lae_ = 0D;
        bitField0_ = (bitField0_ & ~0x00000002);
        dir_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.lili.proto.java.CoachReportProto.internal_static_com_lili_proto_pb_CoachReportMsg_descriptor;
      }

      public com.lili.proto.java.CoachReportProto.CoachReportMsg getDefaultInstanceForType() {
        return com.lili.proto.java.CoachReportProto.CoachReportMsg.getDefaultInstance();
      }

      public com.lili.proto.java.CoachReportProto.CoachReportMsg build() {
        com.lili.proto.java.CoachReportProto.CoachReportMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.lili.proto.java.CoachReportProto.CoachReportMsg buildPartial() {
        com.lili.proto.java.CoachReportProto.CoachReportMsg result = new com.lili.proto.java.CoachReportProto.CoachReportMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.lge_ = lge_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.lae_ = lae_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.dir_ = dir_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.lili.proto.java.CoachReportProto.CoachReportMsg) {
          return mergeFrom((com.lili.proto.java.CoachReportProto.CoachReportMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.lili.proto.java.CoachReportProto.CoachReportMsg other) {
        if (other == com.lili.proto.java.CoachReportProto.CoachReportMsg.getDefaultInstance()) return this;
        if (other.hasLge()) {
          setLge(other.getLge());
        }
        if (other.hasLae()) {
          setLae(other.getLae());
        }
        if (other.hasDir()) {
          setDir(other.getDir());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasLge()) {
          
          return false;
        }
        if (!hasLae()) {
          
          return false;
        }
        if (!hasDir()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.lili.proto.java.CoachReportProto.CoachReportMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.lili.proto.java.CoachReportProto.CoachReportMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required double lge = 1;
      private double lge_ ;
      /**
       * <code>required double lge = 1;</code>
       */
      public boolean hasLge() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required double lge = 1;</code>
       */
      public double getLge() {
        return lge_;
      }
      /**
       * <code>required double lge = 1;</code>
       */
      public Builder setLge(double value) {
        bitField0_ |= 0x00000001;
        lge_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required double lge = 1;</code>
       */
      public Builder clearLge() {
        bitField0_ = (bitField0_ & ~0x00000001);
        lge_ = 0D;
        onChanged();
        return this;
      }

      // required double lae = 2;
      private double lae_ ;
      /**
       * <code>required double lae = 2;</code>
       */
      public boolean hasLae() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required double lae = 2;</code>
       */
      public double getLae() {
        return lae_;
      }
      /**
       * <code>required double lae = 2;</code>
       */
      public Builder setLae(double value) {
        bitField0_ |= 0x00000002;
        lae_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required double lae = 2;</code>
       */
      public Builder clearLae() {
        bitField0_ = (bitField0_ & ~0x00000002);
        lae_ = 0D;
        onChanged();
        return this;
      }

      // required float dir = 3;
      private float dir_ ;
      /**
       * <code>required float dir = 3;</code>
       */
      public boolean hasDir() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required float dir = 3;</code>
       */
      public float getDir() {
        return dir_;
      }
      /**
       * <code>required float dir = 3;</code>
       */
      public Builder setDir(float value) {
        bitField0_ |= 0x00000004;
        dir_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required float dir = 3;</code>
       */
      public Builder clearDir() {
        bitField0_ = (bitField0_ & ~0x00000004);
        dir_ = 0F;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.lili.proto.pb.CoachReportMsg)
    }

    static {
      defaultInstance = new CoachReportMsg(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.lili.proto.pb.CoachReportMsg)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_lili_proto_pb_CoachReportMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_lili_proto_pb_CoachReportMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n-main/java/com/lili/proto/pb/CoachRepor" +
      "t.proto\022\021com.lili.proto.pb\"7\n\016CoachRepor" +
      "tMsg\022\013\n\003lge\030\001 \002(\001\022\013\n\003lae\030\002 \002(\001\022\013\n\003dir\030\003 " +
      "\002(\002B\'\n\023com.lili.proto.javaB\020CoachReportP" +
      "roto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_lili_proto_pb_CoachReportMsg_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_lili_proto_pb_CoachReportMsg_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_lili_proto_pb_CoachReportMsg_descriptor,
              new java.lang.String[] { "Lge", "Lae", "Dir", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}